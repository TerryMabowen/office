package com.mbw.office.sso.spi.zookeeper;

import com.mbw.office.common.lang.exception.ServiceException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.server.quorum.flexible.QuorumVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * zookeeper客户端
 *
 * @author Mabowen
 * @date 2020-05-06 11:56
 */
@Slf4j
@Component
public class ZkClient {
    @Getter
    @Autowired
    private ZkConfig zkConfig;

    private CuratorFramework client;

    /**
     * 创建节点---创建一个有数据的节点
     *
     * @param mode     节点类型
     *                 1、PERSISTENT 持久化节点 持久化目录节点，存储的数据不会丢失。如果重复创建，客户端会抛出NodeExistsException异常.
     *                 2、PERSISTENT_SEQUENTIAL 持久化时序节点 顺序自动编号的持久化目录节点，存储的数据不会丢失, 如果重复创建，不会抛出NodeExistsException异常，会创建2个节点
     *                 3、EPHEMERAL 临时目录节点，一旦创建这个节点的客户端与服务器端口也就是session 超时，这种节点会被自动删除
     *                 4、EPHEMERAL_SEQUENTIAL 临时时序节点 临时自动编号节点，一旦创建这个节点的客户端与服务器端口也就是session 超时，这种节点会被自动删除，并且根据当前已近存在的节点数自动加 1，然后返回给客户端已经成功创建的目录节点名。
     * @param path     节点名称
     * @param nodeData 节点数据
     */
    public void createNodeWithData(CreateMode mode, String path, String nodeData) {
        try {
            //使用creatingParentsIfNeeded()之后Curator能够自动递归创建所有所需的父节点
            client.create()
                    .creatingParentsIfNeeded()
                    .withMode(mode)
                    .forPath(path, nodeData.getBytes(StandardCharsets.UTF_8));
        } catch (Exception exp) {
            throw new ServiceException("创建节点失败：" + exp.getMessage(), exp);
        }
    }

    /**
     * 创建节点---创建一个无数据的节点
     *
     * @param mode 节点类型
     *             1、PERSISTENT 持久化目录节点，存储的数据不会丢失, 不删除就会永久存在。
     *             2、PERSISTENT_SEQUENTIAL 顺序自动编号的持久化目录节点，存储的数据不会丢失
     *             3、EPHEMERAL 临时目录节点，一旦创建这个节点的客户端与服务器端口也就是session 超时，这种节点会被自动删除
     *             4、EPHEMERAL_SEQUENTIAL 临时自动编号节点，一旦创建这个节点的客户端与服务器端口也就是session 超时，这种节点会被自动删除，并且根据当前已近存在的节点数自动加 1，然后返回给客户端已经成功创建的目录节点名。
     * @param path 节点名称
     */
    public void createNodeWithoutData(CreateMode mode, String path) {
        try {
            //使用creatingParentContainersIfNeeded()之后Curator能够自动递归创建所有所需的父节点
            client.create()
                    .creatingParentsIfNeeded()
                    .withMode(mode)
                    .forPath(path);
        } catch (Exception exp) {
            throw new ServiceException("创建节点异常：" + exp.getMessage(), exp);
        }
    }

    /**
     * 设置指定节点的数据
     *
     * @param path
     * @param data
     */
    public void setNodeData(String path, byte[] data) {
        try {
            client.setData().forPath(path, data);
        } catch (Exception exp) {
            throw new ServiceException("设置指定节点的数据异常：" + exp.getMessage(), exp);
        }
    }

    /**
     * 获取指定节点的数据
     *
     * @param path
     * @return
     */
    public byte[] getNodeData(String path) {
        try {
            return client.getData().forPath(path);
        } catch (Exception exp) {
            throw new ServiceException("获取指定节点的数据异常：" + exp.getMessage(), exp);
        }
    }

    /**
     * 获取数据时先同步
     *
     * @param path
     * @return
     */
    public byte[] synNodeData(String path) {
        client.sync();
        return getNodeData(path);
    }

    /**
     * 判断路径是否存在
     *
     * @param path
     * @return true:存在，false:不存在或异常
     */
    public boolean isExistNode(final String path) {
        try {
            client.sync();
            return null != client.checkExists().forPath(path);
        } catch (Exception exp) {
            log.error("判断路径是否存在出现异常：" + exp.getMessage(), exp);
            return false;
        }
    }


    /**
     * 获取节点的子节点
     *
     * @param path
     * @return
     */
    public List<String> getChildren(String path) {
        try {
            return client.getChildren().forPath(path);
        } catch (Exception exp) {
            throw new ServiceException("获取节点的子节点异常：" + exp.getMessage(), exp);
        }
    }

    /**
     * 删除节点---默认方法
     *
     * @param path
     */
    public void defaultDeleteNode(final String path) {
        try {
            deleteNode(path, true);
        } catch (Exception exp) {
            throw new ServiceException("删除节点数据异常：" + exp.getMessage(), exp);
        }
    }


    /**
     * 删除节点
     *
     * @param path
     * @param deleteChildren 是否删除子节点
     */
    public void deleteNode(final String path, Boolean deleteChildren) {
        try {
            if (deleteChildren) {
                //guaranteed()删除一个节点，强制保证删除,
                // 只要客户端会话有效，那么Curator会在后台持续进行删除操作，直到删除节点成功
                //deletingChildrenIfNeeded() 如果需要删除子节点
                client.delete()
                        .guaranteed()
                        .deletingChildrenIfNeeded()
                        .forPath(path);
            } else {
                client.delete()
                        .guaranteed()
                        .forPath(path);
            }
        } catch (Exception exp) {
            throw new ServiceException("删除节点数据异常：" + exp.getMessage(), exp);
        }
    }

    /**
     * 创建zookeeper客户端
     *
     * @author Mabowen
     * @date 20:04 2020-06-04
     */
    public void buildZookeeperClient() {
        if (client == null) {
            if (zkConfig == null) {
                log.error("ZkConfig is NULL");
                throw new ServiceException("ZkConfig is NULL");
            }

            log.info("zookeeper客户端开始创建");
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(zkConfig.getBaseSleepTimeMs(), zkConfig.getMaxRetry());
            client = CuratorFrameworkFactory.builder()
                    .connectString(zkConfig.getAddress())
                    .sessionTimeoutMs(zkConfig.getSessionTimeout())
                    .connectionTimeoutMs(zkConfig.getConnectTimeout())
                    .retryPolicy(retryPolicy)
                    .build();

            try {
                zkConnectionListener();
            } catch (KeeperException.SessionExpiredException e) {
                QuorumVerifier currentConfig = client.getCurrentConfig();
                System.out.println(currentConfig.toString());
                log.error("无法重新连接到ZooKeeper服务，session已经过期: " + e.getMessage(), e);
            } catch (KeeperException.ConnectionLossException e) {
                log.error("无法重新连接到ZooKeeper服务，连接丢失: " + e.getMessage(), e);
            } catch (Exception e) {
                log.error("ZooKeeper服务异常：" + e.getMessage(), e);
            }

            client.start();
            log.info("zookeeper客户端启动成功");
        }
    }

    /**
     * zookeeper连接监听
     *
     * @author Mabowen
     * @date 20:05 2020-06-04
     */
    private void zkConnectionListener() throws KeeperException.SessionExpiredException, KeeperException.ConnectionLossException {
        //zkClient连接状态监测器
        client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
            @Override
            public void stateChanged(CuratorFramework client, ConnectionState state) {
                if (state == ConnectionState.LOST) {
                    //连接丢失
                    log.warn("zookeeper连接丢失");
                } else if (state == ConnectionState.CONNECTED) {
                    //连接新建
                    log.info("zookeeper连接成功");
                } else if (state == ConnectionState.RECONNECTED) {
                    //重新连接
                    log.info("zookeeper重新连接");
                }
            }
        });
    }

    /**
     * spring容器释放zkClient时，关闭zookeeper客户端
     *
     * @author Mabowen
     * @date 20:05 2020-06-04
     */
    @PreDestroy
    private void stop() {
        log.info("zookeeper客户端关闭");
        client.close();
    }

}
