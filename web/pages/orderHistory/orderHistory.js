const db = wx.cloud.database();
const record = db.collection('record');
const app = getApp();

Page({

  onLoad: function (options) {
    var username = app.username;
    record.where({
      username: username
    }).get({
      success: res => {
        var items = [];
        for (let i = 0; i < res.data.length; i++){
          items[i] = res.data[i];
          if (items[i].flag == '0') {
            items[i].flag = '有';
          } else {
            items[i].flag = '无';
          }
        }
        this.setData({
          items: items
        })
      }
    })
  },

})