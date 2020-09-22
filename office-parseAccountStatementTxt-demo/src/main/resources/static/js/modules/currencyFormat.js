/**
 * 对前端展示的金额进行格式化，使其以千分位显示
 */
var formatCurrency = function format(num) {
    if (num === null || num === undefined || '' === num) {
        return "0.00";
    } else {
        if (0 === num || isNaN(num)) {
            return "0.00";
        }
        //将num中的$,去掉，将num变成一个纯粹的数据格式字符串
        num = num.toString().replace(/[$,]/g, '');
        //如果num是负数，则获取她的符号
        var sign = num.indexOf("-") >= 0 ? '-' : '';
        //如果存在小数点，则获取数字的小数部分
        var cents = num.indexOf(".") >= 0 ? num.substr(num.indexOf(".")) : '';
        cents = cents.length > 1 ? cents : '.00';
        //如果小数部分只有一位，则在后面补上0
        if (cents.length == 2) {
            cents = cents + '0';
        }
        //获取数字的整数数部分
        var no = num.indexOf(".") >= 0 ? num.substring(0, (num.indexOf("."))) : num;
        no = no.replace('-', '');
        //如果整数部分长度大于1，则不能以0开头
        if (no.length > 1 && '0' == no.substr(0, 1)) {
            no = no.substring(1, no.length);
        }
        //针对整数部分进行格式化处理，这是此方法的核心，也是稍难理解的一个地方，逆向的来思考或者采用简单的事例来实现就容易多了
        /*
          也可以这样想象，现在有一串数字字符串在你面前，如果让你给他家千分位的逗号的话，你是怎么来思考和操作的?
          字符串长度为0/1/2/3时都不用添加
          字符串长度大于3的时候，从右往左数，有三位字符就加一个逗号，然后继续往前数，直到不到往前数少于三位字符为止
         */
        // console.log(no);
        for (var i = 0; i < Math.floor((no.length - (1 + i)) / 3); i++) {
            no = no.substring(0, no.length - (4 * i + 3)) + ',' + no.substring(no.length - (4 * i + 3));
        }

        if (no.charAt(0) == ',') {
            no = no.substring(1, no.length);
        }

        //将数据（符号、整数部分、小数部分）整体组合返回
        return (sign + no + cents);
    }
};

var currencyFormat = {
    formatCurrency: formatCurrency
};