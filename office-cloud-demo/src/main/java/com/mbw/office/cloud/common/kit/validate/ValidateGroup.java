package com.mbw.office.cloud.common.kit.validate;

/**
 * 验证组
 * 示例：
 * 1. @NotNull(message = "xxx不能为空", groups = {ValidateGroup.Update.class}) 只有更新时验证
 * 2. @NotNull(message = "xxx不能为空", groups = {ValidateGroup.Insert.class}) 只有新增时验证
 * 3. @NotNull(message = "xxx不能为空", groups = {ValidateGroup.Update.class, ValidateGroup.Insert.class}) 新增+更新都验证
 *
 * 使用：
 * 1. Result result = validate(obj, ValidateGroup.Update.class);
 * 2. Result result = validate(obj, ValidateGroup.Insert.class);
 * 3. Result result = validate(fb, ValidateGroup.Update.class, ValidateGroup.Insert.class);
 * @author Mabowen
 * @date 2020-11-30 18:12
 */
public interface ValidateGroup {

    interface Insert {}

    interface Update {}
}
