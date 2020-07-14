package com.mbw.office.tree;

import lombok.Data;

import java.util.List;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-07-13 14:06
 */
@Data
public class DepartmentTree {
    private Long id;

    private Long parentId;

    private String name;

    private List<DepartmentTree> children;

    public DepartmentTree(Long id, Long parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }
}
