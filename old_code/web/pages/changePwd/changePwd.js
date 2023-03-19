const db = wx.cloud.database();
const patient = db.collection('patient');
var app = getApp();
//正则表达式
//密码格式：以字母开头，长度在6-18之间，只能包含字符、数字和下划线
var regPwd = new RegExp('[a-zA-Z0-9]{5,17}$');

Page({
  changePwd: function (event) {
    var newPwd = event.detail.value.newPwd;
    var ensureNewPwd = event.detail.value.ensureNewPwd;
    if (newPwd != ensureNewPwd) {
      wx.showToast({
        title: '两次输入密码不同！',
        icon:'none',
        duration: 1000,
      })
    }
    else{
      patient.doc(app._id).update({
        data:{
          pwd: newPwd
        }
      })
      wx.showToast({
        title: '修改成功！',
        icon:'success',
        duration:1000,
      })
    }
  },

  checkPwd: function (event) {
    var pwd = event.detail.value;
    //密码格式不符合要求
    if (!regPwd.test(pwd)) {
      wx.showToast({
        title: '密码格式错误！密码长度在6-18位之间，且只能包含字母和数字',
        icon:'none',
        duration:2500
      })
    }
  },

})