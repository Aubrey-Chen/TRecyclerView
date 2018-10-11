# TRecyclerView<br/>

由于项目需求,很多地方使用了大量的多类型item，就做了下封装，感觉再怎么封装依旧不是很完美，于是捣鼓捣鼓，自己弄了套刷新库，主要是整合了针对单类型,多类型的item开发,支持下拉刷新，加载更多功能的TRecyclerView，旨意在于让开发者只关心ViewHolder的开发，高复用,TRecyclerView(面向ViewHolder开发的刷新库,多类型item终结者,好不好用你试试就知道) <br/>
### 主要功能<br/>
   * 下拉刷新、加载更多；<br/>
   * 自定义下拉动画；<br/>
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

     com.github.SelfZhangTQ:TRecyclerView:2.5.1


 Step 3. Item类型配置<br/>

    MultiTypeAdapter adapter = new MultiTypeAdapter.Builder<>()
            //设置刷新头 如果有刷新需求，此代码不配置，则不具有刷新功能，ProgressStyle.Pacman配置刷新样式
           .bind(HeaderVo.class, new HeaderViewHolder(this, ProgressStyle.Pacman));
            //设置item1，
           .bind(Bean1.class, new ItemView1(this))
           //设置item2，
           .bind(Bean2.class, new ItemView2(this))
           //加载更多，如果不需要加载更多，此代码可不配置
           .bind(FootVo.class, new FootViewHolder(this, ProgressStyle.Pacman))
           .build();
    
    //数据容器
    items = new Items();

    layoutManager = new LinearLayoutManager(LinearLayoutActivity.this);
    mRecyclerView.setAdapter(adapter);
    mRecyclerView.setLayoutManager(layoutManager);


 Step 4. 自定义刷新动画配置和加载更多描述配置<br/>
       
      //自定义刷新头 需要实现OnTouchMoveListener接口，
      RefreshHeader refreshHeader = new RefreshHeader(this);
     .bind(HeaderVo.class,newHeaderViewHolder(LinearLayoutActivity.this,refreshHeader, new refreshHeader.getOnTouchMoveListener()))
                
                
    //加载更多描述配置，用4个参数的构造方法即可。
    new FootViewHolder(this, ProgressStyle.SysProgress,"努力加载","没有更多啦。。")；          

 Step 5.下拉刷新,加载更多,滚动监听回调<br/>

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

  Step 6.刷新完成或加载更多完成后的操作<br/>
   
    //刷新完成，有更多
    mRecyclerView.refreshComplete(items,false);
    
    //注：如果默认加载不够一页数,即没有更多
    mRecyclerView.refreshComplete(items,true);
   
    
    //加载更多完成，还有分页数据，
    //此处是服务器返回的数据集合，并非是所有数据集合
    mRecyclerView.loadMoreComplete(items,false);
    
    // 注：如果默认加载不够一页数,即没有更多
    mRecyclerView.refreshComplete(items,true);

    // 局部刷新
    mRecyclerView.notifyItemRangeChanged(position,1);


  Step 7. 添加Item点击事件

        adapter.setOnItemClickListener(this);//实现OnItemClickListener 接口

        //在holder中设置监听事件
        public class ItemType extends AbsViewHolder<ItemVo, ItemType1.ViewHolder> {
            public ItemType1(Context context) {
                super(context);
            }

            @Override
            public int getLayoutResId() {
                return R.layout.type_1;
            }

            @Override
            public ViewHolder createViewHolder(View view) {
                return new ViewHolder(view,mOnItemClickListener);
            }

            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ItemVo item) {
                holder.tvType.setText(item.type);
            }

            static class ViewHolder extends BaseHolder {

                TextView tvType;

                ViewHolder(@NonNull final View itemView, final OnItemClickListener mOnItemClickListener) {
                    super(itemView);
                    tvType = getViewById(R.id.tv_type);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (null!=mOnItemClickListener){
                                mOnItemClickListener.onItemClick(v,getAdapterPosition(),itemView.getTag());
                            }

                        }
                    });
                }

            }

        }

      项目实战使用可参考:<https://github.com/SelfZhangTQ/T-MVVM> <br/>

  Step 8.CoordinatorLayout+AppBarLayout+SwipeRecyclerView使用的问题<br/>
     
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
