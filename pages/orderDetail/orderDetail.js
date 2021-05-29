const db = wx.cloud.database();
const record = db.collection('record');
var _id;
var des;

Page({

  onLoad: function (options) {
    _id = options._id;
    record.where({
      _id: _id
    }).get({
      success: res => {
        _id = res.data[0]._id;
        var time = res.data[0].time;
        var doctorName = res.data[0].doctorName;
        var patientName = res.data[0].patientName;
        var patientGender = res.data[0].patientGender;
        var patientAge = res.data[0].patientAge;
        des = res.data[0].des;
        this.setData({
          _id: _id,
          time: time,
          doctorName: doctorName,
          patientName: patientName,
          patientGender: patientGender,
          patientAge: patientAge,
          des: des
        })
      }
    })
  },

  onSubmit: function (event) {
    var reply = event.detail.value.reply;
    record.doc(_id).update({
      data:{
        des: des + ' ~患者：' + reply,
        flag: '1'
      }
    }).then(
      wx.showToast({
        title: '追问成功！',
        icon:'success',
        duration:1500
      })
    )
  }
})