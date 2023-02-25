const db = wx.cloud.database();
const doctor = db.collection('doctor');

Page({
  verify: function (event) {
    var doctorID = event.detail.value.doctorID;
    var phone = event.detail.value.phone;
    doctor.where({
      doctorID: doctorID,
      phone: phone,
    }).get({
      success: res=> {
        if (res.data.length != 0) {
          wx.showToast({
            title: '密码已找回',
            icon:'success',
            duration: 1000,
          })
          this.setData({
            getPwd: '找回的密码是：' + String(res.data[0].pwd),
          })
        }
        else{
          wx.showToast({
            title: '密码找回失败，请联系管理员',
            icon:'none',
            duration:2000,
          })
        }
      },
      fail: res=> {
        console.log(error);
      }
    })
  }
})