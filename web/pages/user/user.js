var app = getApp();

Page({

  onLoad: function (params) {
    this.setData({
      username: app.username
    })
  },

  exitSys: function (params) {
    app.username = null;
    app._id = null;
    app.phone = null;
    app.money = null;
    app.doctorID = null;
    app.doctorName = null;
    wx.redirectTo({
      url: '/pages/login/login',
      success: (res) => {
        wx.showToast({
          title: '退出成功！',
          duration: 500,
          icon:'loading',
          mask: true,
          success: (res) => {},
          fail: (res) => {},
          complete: (res) => {},
        })
      },
      fail: (res) => {},
      complete: (res) => {},
    })
  }
})