const db = wx.cloud.database();
const patient = db.collection("patient");
var app = getApp();

Page({
  //点击登录按钮
  Login: function (event) {
    patient.where({
      username: event.detail.value.username,
      pwd: event.detail.value.pwd
    }).get({
      success: res => {
        //登录成功显示toast, 并跳转到主页
        if(res.data.length == 1){
          wx.switchTab({
            url: '/pages/home/home',
          })
          //将获得的value赋值给全局变量
          app.username = res.data[0].username;
          app._id = res.data[0]._id;
          app.money = res.data[0].money;
          app.phone = res.data[0].phone;
        }
        else{
          wx.showToast({
            title: '用户名或密码错误',
            icon:'none',
            duration: 1000,
          })
        }
      },
      fail: err => {
        console.log(err);
      },
    })
  },

  //点击医生登录，跳转到医生登录页面
  doctorLogin: function (event) {
    wx.navigateTo({
      url: '/pages/doctorLogin/doctorLogin',
    })
  },

  //点击注册，跳转到注册页面
  gotoRegister: function (event) {
    wx.navigateTo({
      url: '/pages/register/register',
    })
  },

  //点击忘记密码，跳转到忘记密码页面
  gotoForgetPwd: function (event) {
    wx.navigateTo({
      url: '/pages/forgetPwd/forgetPwd',
    })
  }
})