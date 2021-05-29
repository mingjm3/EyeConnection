const db = wx.cloud.database();
const feedback = db.collection('feedback');
const app = getApp();

Page({
  submitFeedback: function (event) {
    var today = new Date();
    var today = today.getFullYear() + '-' + (today.getMonth()+1) + '-' + today.getDate() + ' ' + today.getHours() + ':' + today.getMinutes();
    var feedback_content = event.detail.value.feedback;
    var username = app.username;
    var doctorName = app.doctorName;
    feedback.add({
      data:{
        feedback: feedback_content,
        username: username,
        doctorName: doctorName,
        date: today
      }
    })
   wx.showToast({
     title: '感谢您的反馈！',
     icon:'success',
     duration:1000
   })
  }
})