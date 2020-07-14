package com.mbw.office.tree;

import com.mbw.office.common.util.tree.TreeUtil;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

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
