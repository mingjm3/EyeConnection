
Page({
  onLoad: function (options) {
    this.setData({
      drugName: options.drugName,
      drugID: options.drugID,
      drugImg: options.drugImg,
      drugDes: options.drugDes
    })
  },
})