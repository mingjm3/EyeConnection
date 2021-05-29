const db = wx.cloud.database();
const record = db.collection("record");
const patient = db.collection("patient");
const app = getApp();
var doctor = null;

Page({

  onLoad: function (options) {
    //页面加载时获得患者选择的医生的名字
    doctor = options.doctor;
    wx.showModal({
      confirmColor: '#40a9ff',
      confirmText: '我已知晓',
      content: '警告：网上问诊不可代替线下面诊！',
      showCancel: false,
      success: (result) => {},
      fail: (res) => {},
      complete: (res) => {},
    })
  },

  onSubmit: function (event) {
    //获得视图层传来的patient数据
    var patientName = event.detail.value.patientName;
    var patientGender = event.detail.value.patientGender;
    var patientAge = event.detail.value.patientAge;
    var patientDes = event.detail.value.patientDes;
    
    //获得当前时间
    var timeNow = new Date();
    timeNow = timeNow.getFullYear() + '-' + (timeNow.getMonth()+1) + '-' + timeNow.getDate()

    //向record数据表中添加就诊记录
    record.add({
      data: {
        username: app.username,
        patientName: patientName,
        patientGender: patientGender,
        patientAge: patientAge,
        des: '患者：' + patientDes,
        doctorName: doctor,
        time: timeNow,
        flag: '1'
      }
    }).then(res => {
      wx.showToast({
        title: '提交成功!',
        icon:'success',
        duration:1500
      })
    })

    //更新patient表中的余额
    patient.doc(app._id).update({
      data:{
        money: (app.money-5)
      }
    })
  } 
})