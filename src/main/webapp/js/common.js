function delayURL() {
    const span = $('span');
    let x = span.html(); //获取到页面的倒计时时间，由于获取到是字符串，需通过Number转换成数字进行接下来计算
    console.log("倒计时：" + x);
    x--;
    if (x > 0) {
        span.html(x);   //将计算后的时间赋值到标签里
    } else {
        location.href = 'main.html';  //要跳转的页面
        clearInterval(timer);     //清除定时器
    }
}
