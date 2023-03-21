const db = wx.cloud.database();
const record = db.collection('record');

Page({
  
  onLoad: function (options) {
    var app = getApp();
    record.where({
      doctorName: app.doctorName,
      flag: '1'
    }).get({
      success: res => {
        console.log(res);
        var items = [];
        for (let i = 0; i < res.data.length; i++){
          items[i] = res.data[i];
        }
        this.setData({
          items: items
        })
      }
    })
  },
})