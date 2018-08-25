# TRecyclerView<br/>

由于项目需求,很多地方使用了大量的多类型item，于是捣鼓捣鼓诞生了TRecyclerView，让开发者只关心ViewHolder的开发，高复用,TRecyclerView(面向ViewHolder开发的刷新库,多类型item终结者,好不好用你试试就知道) <br/>
### 主要功能<br/>
   * 下拉刷新、加载更多；<br/>
   * 高复用,支持多类型；<br/>
   * ...<br/>
   项目github地址:<https://github.com/SelfZhangTQ/TRecyclerView> <br/>

#### 效果图 <br/>
![image](https://github.com/SelfZhangTQ/TRecyclerView/raw/master/screenshots/111.png)
![image](https://github.com/SelfZhangTQ/TRecyclerView/raw/master/screenshots/222.png)<br/>
![image](https://github.com/SelfZhangTQ/TRecyclerView/raw/master/screenshots/333.png)
![image](https://github.com/SelfZhangTQ/TRecyclerView/raw/master/screenshots/444.png)<br/>
![image](https://github.com/SelfZhangTQ/TRecyclerView/raw/master/screenshots/555.png)
![image](https://github.com/SelfZhangTQ/TRecyclerView/raw/master/screenshots/666.png)<br/>
![image](https://github.com/SelfZhangTQ/TRecyclerView/raw/master/screenshots/777.png)
![image](https://github.com/SelfZhangTQ/TRecyclerView/raw/master/screenshots/888.png)<br/>


#### 使用步骤 <br/>
  Gradle<br/>
 Step 1. 在你的根build.gradle文件中增加JitPack仓库依赖。

     allprojects {
         repositories {
          jcenter()
          maven { url "https://jitpack.io" }
        }
     }

 Step 2. 在你的model的build.gradle文件中增加TRecyclerView依赖<br/>

     com.github.SelfZhangTQ:TRecyclerView:2.2.4

 Step 3.数据填充<br/>

    adapter = new MultiTypeAdapter();
    //设置刷新头，加载更多foot以及itemView
    adapter.bind(HeaderVo.class, new HeaderViewHolder(LinearLayoutActivity.this, ProgressStyle.Pacman));
    adapter.bind(FootVo.class, new FootViewHolder(LinearLayoutActivity.this, ProgressStyle.Pacman));

    items = new Items();


    layoutManager = new LinearLayoutManager(LinearLayoutActivity.this);
    mRecyclerView.setAdapter(adapter);
    mRecyclerView.setLayoutManager(layoutManager);

 Step 4.下拉刷新,加载更多,滚动监听回调<br/>

    mRecyclerView.addOnRefreshListener(new OnRefreshListener(){
            @Override
            public void onRefresh() {

             }
            @Override
            public void onLoadMore() {

            }
        });
    mRecyclerView.addOnTScrollListener(new OnTScrollListener() {
            @Override
            public void onScrolled(int dx, int dy) {

            }

            @Override
            public void onScrollStateChanged(int state) {

            }
        });



 TRecyclerView使用步骤介绍完了，对了adapter好像忘了介绍，NO,NO,NO,你不需要关心adapter,只需要写对应UI以及数据设置就行，尽情的编写ViewHolder吧


 项目github地址:<https://github.com/SelfZhangTQ/TRecyclerView> <br/>
项目实战地址github地址:<https://github.com/SelfZhangTQ/T-MVVM> <br/>

效果图：<br/>
![image](https://github.com/SelfZhangTQ/TRecyclerView/raw/master/screenshots/9_video.gif)<br/>

#### 第三方库 <br/>
* multitype <br/>
* AVLoadingIndicatorView <br/>


