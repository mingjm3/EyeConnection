App({
  onLaunch: function(options) {
    //全局初始化云服务
    wx.cloud.init({
      //用云ID配置环境
      env:"clould-system-3gfn78g245962f54"
    });
  },
  
  //初始化全局变量
  _id:null,
  username: null,
  phone:null,
  money:null,
  doctorID: null,
  doctorName: null
});
  