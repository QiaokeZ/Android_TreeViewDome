package com.etraffic.treeviewdome;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private ListViewAdapter adapter;
    private TreeViewDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        listView = findViewById(R.id.list);
        listView.setOnItemClickListener(this);
        adapter = new ListViewAdapter();
        listView.setAdapter(adapter);
    }

    public void add(View view) {
        TestModel node = new TestModel();
        node.name = "手机品牌";
        node.child = new ArrayList();

        TestModel node1 = new TestModel();
        node1.name = "Apple";
        node1.maginLeft = 100;
        node1.child = new ArrayList();
        node.child.add(node1);

        TestModel node2 = new TestModel();
        node2.name = "颜色";
        node2.maginLeft = 200;
        node2.child = new ArrayList();
        node1.child.add(node2);

        TestModel node3 = new TestModel();
        node3.name = "黑色";
        node3.maginLeft = 300;
        node3.child = new ArrayList();
        node2.child.add(node3);

        TestModel node4 = new TestModel();
        node4.name = "iPhoneX";
        node4.maginLeft = 300;
        node3.child.add(node4);

        List nodes = dataSource.getNodes();
        nodes.add(node);
        dataSource.updateNodes();
        adapter.notifyDataSetChanged();
    }

    public void show(View view) {
        List list = getSelectedNodes();
        Toast.makeText(this, list.toString(), Toast.LENGTH_LONG).show();
    }

    public List<TestModel> getSelectedNodes() {
        List result = new ArrayList();
        for (int i = 0; i < dataSource.getElements().size(); i++) {
            TestModel model = (TestModel) dataSource.getElements().get(i);
            if (model.isSelected) {
                result.add(model);
            }
        }
        return result;
    }

    private void initData() {
        List list = new ArrayList();

        TestModel node = new TestModel();
        node.name = "食物";
        node.child = new ArrayList();
        list.add(node);

        TestModel node1 = new TestModel();
        node1.name = "水果";
        node1.maginLeft = 100;
        node1.child = new ArrayList();
        node.child.add(node1);

        TestModel node10 = new TestModel();
        node10.name = "苹果";
        node10.maginLeft = node1.maginLeft;
        node1.child.add(node10);

        TestModel node11 = new TestModel();
        node11.name = "香蕉";
        node11.maginLeft = node10.maginLeft;
        node1.child.add(node11);

        TestModel node12 = new TestModel();
        node12.name = "梨子";
        node12.maginLeft = node10.maginLeft;
        node1.child.add(node12);

        TestModel node2 = new TestModel();
        node2.name = "蔬菜";
        node2.maginLeft = node1.maginLeft;
        node2.child = new ArrayList();
        node.child.add(node2);

        TestModel node20 = new TestModel();
        node20.name = "白菜";
        node20.maginLeft = node1.maginLeft;
        node2.child.add(node20);

        TestModel node21 = new TestModel();
        node21.name = "西红柿";
        node21.maginLeft = node1.maginLeft;
        node2.child.add(node21);

        TestModel node22 = new TestModel();
        node22.name = "胡萝卜";
        node22.maginLeft = node1.maginLeft;
        node2.child.add(node22);

        TestModel node3 = new TestModel();
        node3.name = "肉类";
        node3.maginLeft = node1.maginLeft;
        node3.child = new ArrayList();
        node.child.add(node3);

        TestModel node30 = new TestModel();
        node30.name = "猪肉";
        node30.maginLeft = node1.maginLeft;
        node3.child.add(node30);

        TestModel node31 = new TestModel();
        node31.name = "鱼肉";
        node31.maginLeft = node1.maginLeft;
        node3.child.add(node31);

        TestModel node32 = new TestModel();
        node32.name = "牛肉";
        node32.maginLeft = node1.maginLeft;
        node3.child.add(node32);

        dataSource = new TreeViewDataSource(list);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TestModel node = (TestModel) dataSource.getElements().get(position);
        node.isExpand = !node.isExpand;
        if (node.child == null) {
            node.isSelected = !node.isSelected;
            adapter.notifyItemChanged(listView, position);
        } else {
            dataSource.updateNodes();
            adapter.notifyDataSetChanged();
        }
    }

    class ListViewAdapter extends BaseListViewAdapter<BaseListViewAdapter.BaseListViewHolder> {

        @Override
        protected BaseListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_tree, parent, false);
            TreeViewHolder holder = new TreeViewHolder(view);
            return holder;
        }

        @Override
        protected void onBindViewHolder(BaseListViewHolder holder, final int position) {
            TestModel node = (TestModel) getItem(position);
            TreeViewHolder treeViewHolder = (TreeViewHolder) holder;
            treeViewHolder.textView.setText(node.name);
            treeViewHolder.itemView.setPadding(node.maginLeft, 0, 0, 0);

            //技能选择开关
            if (node.isSelected) {
                treeViewHolder.ivSelected.setImageDrawable(getResources().getDrawable((R.drawable.ic_yes)));
            } else {
                treeViewHolder.ivSelected.setImageDrawable(getResources().getDrawable((R.drawable.ic_no)));
            }

            //是否展开
            if (node.isExpand) {
                treeViewHolder.ivExpand.setImageDrawable(getResources().getDrawable((R.drawable.arrow_down)));
            } else {
                treeViewHolder.ivExpand.setImageDrawable(getResources().getDrawable((R.drawable.arrow_right)));
            }

            if (node.child != null && node.child.size() > 0) {
                treeViewHolder.ivExpand.setVisibility(View.VISIBLE);
                treeViewHolder.ivSelected.setVisibility(View.INVISIBLE);
            } else {
                treeViewHolder.ivExpand.setVisibility(View.INVISIBLE);
                treeViewHolder.ivSelected.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public int getCount() {
            return dataSource.getElements().size();
        }

        @Override
        public Object getItem(int position) {
            return dataSource.getElements().get(position);
        }
    }

    class TreeViewHolder extends BaseListViewAdapter.BaseListViewHolder {

        public TextView textView;
        public ImageView ivSelected;
        public ImageView ivExpand;

        public TreeViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv);
            ivSelected = itemView.findViewById(R.id.iv_selected);
            ivExpand = itemView.findViewById(R.id.iv_expand);
        }
    }
}
