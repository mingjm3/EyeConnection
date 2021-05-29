//引入高德SDK
var amapFile = require('../../libs/amap-wx.js');
var key = "bbe3b641ab8fc767ea70a5dfe0f29d73";
var markersData = [];
Page({
  /**
   * 页面的初始数据
   */
  data: {
    markers: [],
    latitude: '',
    longitude: '',
    textData: {},
    city: '',
    keywords: '医院',
    mapshow:true,
    maptextshow: false,
    tipshow:false,
    tips: {},
    isIos: false, 
    currentLocation:{},
    range:'',
  },
  makertap: function (e) {
    var id = e.markerId;
    var that = this;
    that.showMarkerInfo(markersData, id);
    that.changeMarkerColor(markersData, id);
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (e) {
    var that = this;
     //判断是不是ios系统
    wx.getSystemInfo({
      success: function (res) {
        if (res.platform == "ios") {
          that.setData({
            isIos:true
          });
        }
      }
    });
    //获取当前位置
    that.getMyLocation();
    wx.getSetting({
      success: (res) => {
        // res.authSetting['scope.userLocation'] == undefined  表示 初始化进入该页面
        // res.authSetting['scope.userLocation'] == false  表示 非初始化进入该页面,且未授权
        // res.authSetting['scope.userLocation'] == true  表示 地理位置授权
        if (res.authSetting['scope.userLocation'] != undefined && res.authSetting['scope.userLocation'] != true) {
          wx.showModal({
            title: '是否授权当前位置',
            content: '需要获取您的地理位置，请确认授权，否则无法获取您所需数据',
            success: function (res) {
              if (res.cancel) {
                wx.showToast({
                  title: '授权失败',
                  duration: 1000
                })
                that.setData({
                  textData: {
                    name: 'Error：用户取消授权',
                    address: '无法加载地图，请在设置中允许请求位置信息'
                  }
                });
              } else if (res.confirm) {
                wx.openSetting({
                  success: function (dataAu) {
                    if (dataAu.authSetting["scope.userLocation"] == true) {
                      wx.showToast({
                        title: '授权成功',
                        duration: 1000
                      })
                      //再次授权后，加载地图
                      that.showMap(that.data.keywords);
                    } else {
                      wx.showToast({
                        title: '授权失败',
                        duration: 1000
                      });
                      that.setData({
                        textData: {
                          name: 'Error：授权失败',
                          address: '无法加载地图，请在设置中允许请求位置信息'
                        }
                      });
                    }
                  }             
                })
              }
            }
          })
        } else { 
          //授权后默认加载
          that.showMap(that.data.keywords);
        }
      }
    });
    
  },
  showMap: function (keywords){
    var that = this;
    var myAmapFun = new amapFile.AMapWX({ key: key });
    var params = {
      iconPathSelected: '/icon/point_selected.png',
      iconPath: '/icon/point.png',
      success: function (data) {
        markersData = data.markers;
        var poisData = data.poisData;
        var markers_new = [];
        markersData.forEach(function (item, index) {
          markers_new.push({
            id: item.id,
            latitude: item.latitude,
            longitude: item.longitude,
            iconPath: item.iconPath,
            width: item.width,
            height: item.height
          })
        })
        if (markersData.length > 0) {
          that.setData({
            markers: markers_new
          });
          that.setData({
            city: poisData[0].cityname || ''
          });
          that.setData({
            latitude: markersData[0].latitude
          });
          that.setData({
            longitude: markersData[0].longitude
          });
          that.showMarkerInfo(markersData, 0);
        } else {
          that.setData({
            textData: {
              name: '抱歉，未找到结果',
              address: 'Error：no results found'
            },
            range : ''
          });
        }
 
      },
    }
    params.querykeywords = keywords;
    myAmapFun.getPoiAround(params)
  },
  onInputFocus: function (e) { 
    this.setData({
      mapshow: false,
      tipshow: true
    }); 
  },
  onInputChange: function (e) { 
    var that = this;
    var keywords = e.detail;
    if (keywords==""){
      that.setData({
        mapshow: true,
        tipshow: false
      }); 
    }
    var dataset = e.target.dataset;
    var myAmapFun = new amapFile.AMapWX({ key: key });
    myAmapFun.getInputtips({
      keywords: keywords,
      location: dataset.longitude + ',' + dataset.latitude,
      city: dataset.city,
      success: function (data) {
        if (data && data.tips) {     
          that.setData({
            tips: data.tips
          });   
        }
      }
    })
  },
  bindSearch: function (e) {
    var keywords = e.target.dataset.keywords;
    this.setData({
      mapshow: true,
      tipshow: false
    }); 
    this.showMap(keywords);
  },
  showMarkerInfo: function (data, i) {
    var that = this;
    console.log(that.data.currentLocation);
    //计算距离
    var lat1 = that.data.currentLocation.latitude;
    var lng1 = that.data.currentLocation.longitude;
    var lat2 = data[i].latitude;
    var lng2 = data[i].longitude;
    var distance = that.getDistance(lat1, lng1, lat2, lng2);
    distance = parseInt(distance * 1000);
    if (distance != null && distance>0){
      var range = distance + "米";
      if (distance >= 1000) {
        range = (Math.round(distance / 100) / 10).toFixed(1) + "公里";
      }
    }
    that.setData({
      textData: {
        name: data[i].name,
        address: data[i].address == "" ? '地址不详' : data[i].address,
        latitude: data[i].latitude,
        longitude: data[i].longitude,    
      },
      range: range,
      maptextshow:true
    });
  },
  changeMarkerColor: function (data, i) {
    var markers = [];
    for (var j = 0; j < data.length; j++) {
      if (j == i) {
        data[j].iconPath = "/icon/point_selected.png";
      } else {
        data[j].iconPath = "/icon/point.png";
      }
      markers.push({
        id: data[j].id,
        latitude: data[j].latitude,
        longitude: data[j].longitude,
        iconPath: data[j].iconPath,
        width: data[j].width,
        height: data[j].height
      })
    }
    this.setData({
      markers: markers
    });
  },
  map: function (e) { 
    var that=this;
    wx.openLocation({
      //当前经纬度
      latitude: that.data.textData.latitude,
      longitude: that.data.textData.longitude,
      //缩放级别默认18,缩放比例为5-18
      scale: 10,
      //位置名
      name: that.data.textData.name,
      //详细地址
      address: that.data.textData.address,
      success: function (res) {
      },
      fail: function (err) {
        wx.showToast({
          title: '调用地图失败',
          duration: 1000
        })
      },
    })
  },
  /// <summary>
  /// 给定的经度1，纬度1；经度2，纬度2. 计算2个经纬度之间的距离。
  /// </summary>
  /// <param name="lat1">经度1</param>
  /// <param name="lon1">纬度1</param>
  /// <param name="lat2">经度2</param>
  /// <param name="lon2">纬度2</param>
  /// <returns>距离（公里、千米）</returns>
  getDistance: function (lat1, lon1, lat2, lon2) {
    var EARTH_RADIUS = 6378.137; //地球半径
    var radLat1 = lat1 * Math.PI / 180.0;
    var radLat2 = lat2 * Math.PI / 180.0;
    var radlon1 = lon1 * Math.PI / 180.0;
    var radlon2 = lon2 * Math.PI / 180.0;
     //差值
    var vLat = radLat1 - radLat2;
    var vLon = radlon1 - radlon2;
    //s就是一个球体上的切面，它的圆心即是球心的一个周长最大的圆。
    var s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(vLat / 2), 2) +
      Math.cos(radLat1) * Math.cos(radLat2) *
    Math.pow(Math.sin(vLon / 2), 2)));
    s = s * EARTH_RADIUS;
    var distance = Math.round(s * 10000) / 10000;
    return distance; 
  },
  //获取当前位置
  getMyLocation:function(){
    var that=this;
    wx.getLocation({
      type: 'gcj02',
      success(data) {
        if (data) {
          that.setData({
            currentLocation:data
          });
        }
      }
    });
  },
})