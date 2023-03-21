
Page({
data: {
 cateItems: [
  {
   cate_id: 1,
   cate_name: "近视门诊",
   ishaveChild: true,
   children:
   [
    {
     child_id: 1,
     name: 'X医生',
     image: "/icon/doctor1.png",
     url:"pages/home/home",
    }
   ]
  },
  {
   cate_id: 2,
   cate_name: "白内障门诊",
   ishaveChild: true,
   children:
   [
    {
     child_id: 1,
     name: 'X医生',
     image: "/icon/doctor1.png",
     url:"pages/home/home",
    }
   ]
  },
  {
   cate_id: 3,
   cate_name: "青光眼门诊",
   ishaveChild: true,
   children:
   [
    {
      child_id: 1,
      name: 'X医生',
      image: "/icon/doctor1.png"
     }
   ]
  },
  {
   cate_id: 4,
   cate_name: "糖网门诊",
   ishaveChild: false,
   children: []
  },
  {
    cate_id: 5,
    cate_name: "斜弱视门诊",
    ishaveChild: false,
    children:
    []
   },
   {
    cate_id: 6,
    cate_name: "角膜病门诊",
    ishaveChild: false,
    children:[]
   }, 
   {
    cate_id: 7,
    cate_name: "接触镜门诊",
    ishaveChild: false,
    children:[]
  },
  {
    cate_id: 8,
    cate_name: "眼外伤门诊",
    ishaveChild: false,
    children:[]
   },
   {
    cate_id: 9,
    cate_name: "眼底病门诊",
    ishaveChild: false,
    children:
    []
   },
 ],
 curNav: 1,
 curIndex: 0
},

//事件处理函数 
switchRightTab: function (e) {
 // 获取item项的id，和数组的下标值 
 let id = e.target.dataset.id,
  index = parseInt(e.target.dataset.index);
 // 把点击到的某一项，设为当前index 
 this.setData({
  curNav: id,
  curIndex: index
 })
}
})

