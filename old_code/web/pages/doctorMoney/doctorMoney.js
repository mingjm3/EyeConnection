const db = wx.cloud.database();
const doctor = db.collection('doctor');
var app = getApp();

Page({
  onLoad: function (options) {
    doctor.where({
      doctorID: app.doctorID
    }).get({
      success: res => {
        var money = res.data[0].money;
        this.setData({
          restMoney: money
        })
      }
    })
  },
})