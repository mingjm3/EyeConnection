const db = wx.cloud.database();
const patient = db.collection("patient");
var username = null;
var pwd = null;
var phone = null;
//正则表达式
//密码格式：以字母开头，长度在6-18之间，只能包含字符、数字和下划线
var regPwd = new RegExp('[a-zA-Z0-9]{5,17}$');
//手机号码格式
var regPhone = new RegExp('(13|14|15|17|18)([0-9]){9}$');

Page({

  checkUsername: function (event) {
    username = event.detail.value;
    //检查用户名是否为空
    if (username == '') {
      wx.showToast({
        title: '用户名不能为空！',
        icon:'none',
        duration:1500
      })
    }
    else{
      //检查用户名是否存在
      patient.where({
          username: username
      }).get({
        success: res => {
          if (res.data.length != 0) {
            wx.showToast({
              title: '用户名已存在!',
              icon:'none',
              duration:1500
            })
          }
        },
        fail: res => {
          console.error();
        }
      })
    }
  },

  checkPwd: function (event) {
    pwd = event.detail.value;
    //密码格式不符合要求
    if (!regPwd.test(pwd)) {
      wx.showToast({
        title: '密码格式错误！密码长度在6-18位之间，且只能包含字母和数字',
        icon:'none',
        duration:2500
      })
    }
  },

  checkEnsurePwd: function (event) {
    var ensurePwd = event.detail.value;
    //确认密码与密码不一致
    if (ensurePwd != pwd) {
      wx.showToast({
        title: '两次密码不同',
        icon:'none',
        duration:1500
      })
    }
  },

  checkPhone: function (event) {
    phone = event.detail.value;
    //检查手机号码格式
    if (!regPhone.test(phone)) {
      wx.showToast({
        title: '手机号码错误',
        icon:'none',
        duration:1500
      })
    }
  },

  //点击注册按钮
  register: function (event) {
    //注册成功
    patient.add({
      data:{
        username: event.detail.value.username,
        pwd: event.detail.value.pwd,
        phone: event.detail.value.phone,
        money: 0
      }
    }).then(
      wx.showModal({
        content:'注册成功!',
        confirmText:'前往登录',
        confirmColor:'#40a9ff',
        showCancel:false,
        success(res){
          if(res.confirm){
            wx.redirectTo({
              url: '/pages/login/login',
            })
          }
        }
      })
    ).catch(err => {
      console.log(err)
    })
  } 
  },
)