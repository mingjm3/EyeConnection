
Page({
  data: {
   cateItems: [
    {
     cate_id: 1,
     cate_name: "常见眼病",
     ishaveChild: true,
     children:
     [
      {
       child_id: 1,
       name: '结膜炎',
       image: "/images/conjunctivitis.png",
       url:"/pages/test/test"
      },
      {
       child_id: 2,
       name: '白内障',
       image: "/images/cataract.png",
       url:"/pages/test/test"
      },
      {
       child_id: 3,
       name: '眼底病',
       image: "/images/fundus.png",
       url:"/pages/test/test"
      }
     ]
    },
    {
     cate_id: 2,
     cate_name: "分类1",
     ishaveChild: false,
     children:
     []
    },
    {
      cate_id: 3,
      cate_name: "分类2",
      ishaveChild: false,
      children:
      []
    },
    {
      cate_id: 4,
      cate_name: "分类3",
      ishaveChild: false,
      children:
      []
    },
    {
      cate_id: 5,
      cate_name: "分类...",
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
  
  