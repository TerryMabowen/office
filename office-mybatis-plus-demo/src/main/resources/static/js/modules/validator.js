//验证身份证号
function checkCardNo(str) {
    let reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
    if (reg.test(card) === false) {
        return false
    } else {
        return true
    }
}

//验证手机号
function checkMobile(str) {
    let re = /^1\d{10}$/
    if (re.test(str)) {
        return true;
    } else {
        return false;
    }
}

function validate(formId, field1, field2) {
    // live 属性: 'submitted',
    // enabled代表当表单控件内容发生变化时就触发验证，默认提交时验证，
    // disabled和submitted代表当点击提交按钮时触发验证
    $('#' + formId).bootstrapValidator({
        // live : 'submitted',
        message: 'Valid Form Data',
        //显示表单验证结果的图标
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        /*
         * digits(message)--整数，numeric(message)--数字，lessThan(value:xx,inclusive:false(true:包含，false:不包含),message)--最大值，greaterThan(value:xx,inclusive:false(true:包含，false:不包含),message)--最小值,
         * between(min:xx,max:xx,message)--xx和xx之间, notEmpty(message)--非空, stringLength(min:xx,max:xx,message)--字符串长度......
         * ps:还有其它验证方式：https://www.cnblogs.com/wang-kai-xuan/p/11031733.html
         */
        fields: {
            field1: {
                validators: {
                    notEmpty: {
                        message: '用户名不能为空'
                    },
                    numeric: {
                        message: '用户名必须为数字'
                    },
                    stringLength: {
                        min: 6,
                        max: 16,
                        message: '用户名长度必须在6-16之间'
                    },
                    // 自定义验证方法
                    callback: {
                        message: '用户名格式错误',
                        callback: function (value, validator) {
                            if (!value) {
                                return true
                            } else if (checkMobile(value)) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }
                }
            },
            field2: {
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    // threshold :  6 , //设置6字符以上开始请求服务器
                    //有待验证，备注以备下次使用。
                    //bootstrap的remote验证器需要的返回结果一定是json格式的数据 :
                    //{"valid":false} //表示不合法，验证不通过
                    //{"valid":true} //表示合法，验证通过
                    //发起Ajax请求。
                    // remote: {
                    //     url: '/url',//验证地址
                    //     data:{password:$('#password').val() },
                    //     message: '密码不正确',//提示消息
                    //     delay : 2000,//设置2秒发起名字验证
                    //     type: 'POST' //请求方式
                    // }
                }
            }
        },
        // submitHandler: function (validator, form, submitButton) {
        //     console.log(validator);
        //     console.log(form);
        //     console.log(submitButton);
        //     alert("submit");
        // }
    });
}

var validator = {
    validate: validate,
    checkMobile: checkMobile,
    checkCardNo: checkCardNo
};