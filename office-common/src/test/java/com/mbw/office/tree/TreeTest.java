package com.mbw.office.tree;

import com.mbw.office.common.util.json.JacksonUtil;
import com.mbw.office.common.util.tree.TreeUtil;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-07-13 14:07
 */
public class TreeTest {

    @Test
    public void f1() {
        List<DepartmentTree> departments = getDepartments();
        System.out.println(Arrays.toString(departments.toArray()));
    }

    @Test
    public void f2() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        List<DepartmentTree> departments = getDepartments();
        List<DepartmentTree> departmentTrees = TreeUtil.buildByRecursive(departments, "Id", "ParentId", "Children");
        System.out.println(Arrays.toString(departmentTrees.toArray()));
    }

    @Test
    public void f3() {
        List<DepartmentTree> departments = getDepartments();
        List<DepartmentTree> tree = listToTree(departments);
        System.out.println(tree);
        System.out.println(JacksonUtil.beanToJson(tree));
    }

    /**
     * 集合转树形
     * @author Mabowen
     * @date 16:40 2020-07-21
     * @param list
     * @return
     */
    private List<DepartmentTree> listToTree(List<DepartmentTree> list) {
        List<DepartmentTree> result = new ArrayList<>();
        Map<Long, DepartmentTree> departmentTreeMap = list.stream().collect(Collectors.toMap(DepartmentTree::getId, Function.identity()));
        for (DepartmentTree departmentTree : list) {
            DepartmentTree tree = departmentTreeMap.get(departmentTree.getParentId());
            if (tree == null) {
                //如果查询出的父部门不存在，说明其为顶级部门
                result.add(departmentTree);
                continue;
            }

            //将查询出的部门加入到父节点中
            if (tree.getChildren() == null) {
                tree.setChildren(new ArrayList<>());
            }
            //因为集合add加入的是地址，所以在这里修改部门属性也会影响到result中的部门对象
            tree.getChildren().add(departmentTree);
        }

        return result;
    }

    private List<DepartmentTree> getDepartments() {
        return Arrays.asList(
                new DepartmentTree(123L, 0L, "父一"),
                new DepartmentTree(124L, 123L, "子一"),
                new DepartmentTree(125L, 124L, "子子一"),
                new DepartmentTree(126L, 123L, "子二"),
                new DepartmentTree(127L, 0L, "父二")
        );
    }
}
