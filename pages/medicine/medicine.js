const db = wx.cloud.database();
const drug = db.collection('drug');

Page({
  search: function (event) {
    var drugName = event.detail.value.drugName;
    //药品名称模糊查询
    drug.where({
        drugName: db.RegExp({
          regexp: drugName,
          options:'i',
        })
      }).get({
      success: res => {
        console.log(res);
        if (res.data.length != 0) {
          drugName = res.data[0].drugName;
          var drugID = res.data[0].drugID;
          var drugImg = res.data[0].drugImg;
          var drugDes = res.data[0].drugDes;
          wx.navigateTo({
            url: '/pages/medicineDetail/medicineDetail?drugName=' + drugName + '&drugID=' + drugID + '&drugDes=' + drugDes + '&drugImg=' + drugImg,
            success: (result) => {},
            fail: (res) => {},
            complete: (res) => {},
          })
        } 
        else {
          wx.showToast({
            title: '未找到该药物',
            icon:'none',
            duration:1500
          })
        }
      } 
    })
  }

})