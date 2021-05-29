const db = wx.cloud.database();
const patient = db.collection("patient");
var app = getApp();

Page({
  onLoad: function (options) {
    patient.where({
      username: app.username
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