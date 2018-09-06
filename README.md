# TRecyclerView<br/>

由于项目需求,很多地方使用了大量的多类型item，于是捣鼓捣鼓出了TRecyclerView，旨意在于让开发者只关心ViewHolder的开发，高复用,TRecyclerView(面向ViewHolder开发的刷新库,多类型item终结者,好不好用你试试就知道) <br/>
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

     com.github.SelfZhangTQ:TRecyclerView:2.4.2

 Step 3.数据填充<br/>

    adapter = new MultiTypeAdapter();
   
    //设置刷新头 如果有刷新需求，此代码可不配置，刷新样式可配置
    adapter.bind(HeaderVo.class, new HeaderViewHolder(this, ProgressStyle.Pacman));
   
    //设置item1，
    adapter.bind(Bean1.class, new ItemView1(this));
    
    //设置item2，
    adapter.bind(Bean2.class, new ItemView2(this));
   
    //加载更多，如果不需要加载更多，此代码可不配置
    adapter.bind(FootVo.class, new FootViewHolder(this, ProgressStyle.Pacman));
    
    //数据容器
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

  Step 5.刷新完成或加载更多完成后的操作<br/>
   
    //刷新完成，有更多
    mRecyclerView.refreshComplete(items,false);
    
    注：如果默认加载不够一页数,即没有更多 mRecyclerView.refreshComplete(items,true);
   
    
    //加载更多完成，还有分页数据，
    mRecyclerView.loadMoreComplete(items,false);
    
     注：如果默认加载不够一页数,即没有更多mRecyclerView.refreshComplete(items,true);
     
  Step 6.CoordinatorLayout+AppBarLayout+SwipeRecyclerView使用的问题<br/>   
     
     由于滑动冲突，滑动到底部加载更多加载时间长问题，需要自定义AppBarLayout.Behavior
     
     <android.support.design.widget.AppBarLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_gravity="top"
        app:elevation="0dp"
        app:layout_behavior="com.trecyclerview.util.AppBarBehavior">
     

 TRecyclerView使用步骤介绍完了，对了adapter好像忘了介绍，NO,NO,NO,你不需要关心adapter,只需要写对应UI以及数据设置就行，尽情的编写ViewHolder吧


 项目github地址:<https://github.com/SelfZhangTQ/TRecyclerView> <br/>
项目实战地址github地址:<https://github.com/SelfZhangTQ/T-MVVM> <br/>

效果图：<br/>
![image](https://github.com/SelfZhangTQ/TRecyclerView/raw/master/screenshots/9_video.gif)<br/>

#### 第三方库 <br/>
* multitype <br/>
* AVLoadingIndicatorView <br/>

#### license <br/>
     
    Copyright 2018 tianqiuzhang
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
#### 最后 <br/>
技术有限，拒绝吐槽，欢迎建议，感谢支持。
