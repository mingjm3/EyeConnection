const db = wx.cloud.database();
const doctor = db.collection("doctor");
var app = getApp();

Page({
  //点击登录按钮
  Login: function (event) {
    doctor.where({
      doctorID: event.detail.value.doctorID,
      pwd: event.detail.value.pwd
    }).get({
      success: res => {
        //登录成功显示toast, 并跳转到主页
        if(res.data.length == 1){
          wx.redirectTo({
            url: '/pages/doctorHome/doctorHome',
          })
          //将获得的value赋值给全局变量
          app.doctorID = res.data[0].doctorID;
          app.doctorName = res.data[0].doctorName;
          app._id = res.data[0]._id;
          app.money = res.data[0].money;
          app.phone = res.data[0].phone;
        }
        else{
          wx.showToast({
            title: '医师号或密码错误',
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

  //点击患者登录，跳转到患者登录页面
  patientLogin: function (event) {
    wx.navigateTo({
      url: '/pages/login/login',
    })
  },

  //点击注册，跳转到医生注册页面
  gotoRegister: function (event) {
    wx.navigateTo({
      url: '/pages/doctorRegister/doctorRegister',
    })
  },

  //点击忘记密码，跳转到忘记密码页面
  gotoForgetPwd: function (event) {
    wx.navigateTo({
      url: '/pages/doctorForgetPwd/doctorForgetPwd',
    })
  }
})