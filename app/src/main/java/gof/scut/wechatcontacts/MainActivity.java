package gof.scut.wechatcontacts;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import gof.scut.common.utils.ActivityUtils;
import gof.scut.common.utils.Log;
import gof.scut.common.utils.StringUtils;
import gof.scut.common.utils.database.CursorUtils;
import gof.scut.common.utils.database.MainTableUtils;
import gof.scut.cwh.models.adapter.ContactsAdapter;


public class MainActivity extends Activity implements View.OnClickListener {

	//Views
	private Button btSearch, btMenu;

	private ListView contacts;
	private Button btAdd, btGroup, btMe;


	//Constants

	//Models
	Cursor cursor;
	MainTableUtils mainTableUtils;

	//Controllers


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
//		Log.d("SPLIT",StringUtils.splitChinese("IK Analyzer是一个开源的，基于java语言" +
//				"开发的轻量级的中文分词工具包。从2006年12月推出1.0版开始， " +
//				"IKAnalyzer已经推出了4个大版本。最初，它是以开源项目Luence为" +
//				"应用主体的，结合词典分词和文法分析算法的中文分词组件。从3.0版" +
//				"本开始，IK发展为面向Java的公用分词组件，独立于Lucene项目，同时" +
//				"提供了对Lucene的默认优化实现。"));

	}

	private void init() {
		//init database
		initDatabase();
		//init Views
		findViews();
		//init handlers //init button listeners, list
		eventHandler();
	}

	private void initDatabase() {
		mainTableUtils = new MainTableUtils(this);

	}

	private void findViews() {
		btSearch = (Button) findViewById(R.id.bt_search);
		btMenu = (Button) findViewById(R.id.bt_menu);

		contacts = (ListView) findViewById(R.id.search_list);
		btAdd = (Button) findViewById(R.id.bt_add);
		btGroup = (Button) findViewById(R.id.bt_group);
		btMe = (Button) findViewById(R.id.bt_me);
	}

	private void eventHandler() {
		btSearch.setOnClickListener(this);
		btMenu.setOnClickListener(this);

		btSearch.setOnClickListener(this);
		btAdd.setOnClickListener(this);
		btGroup.setOnClickListener(this);
		btMe.setOnClickListener(this);

	}

	private void initList() {
		//When clickOnNameAndTel, view
		//When click on image, view label group
		//edit when view
		CursorUtils.closeExistsCursor(cursor);
		cursor = mainTableUtils.selectAllIDName();
		ContactsAdapter adapter = new ContactsAdapter(this, cursor);
		contacts.setAdapter(adapter);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.bt_search:
				ActivityUtils.ActivitySkip(this, SearchActivity.class);

				break;
			case R.id.bt_menu:
				//A MENU WITH SETTING IN IT
				ActivityUtils.ActivitySkip(this, SettingActivity.class);
				break;
			case R.id.bt_add:
				//TO ADD CONTACTS ACTIVITY
				ActivityUtils.ActivitySkip(this, AddContactActivity.class);
				break;
			case R.id.bt_group:
				//TO GROUP VIEW
				ActivityUtils.ActivitySkip(this, LabelsActivity.class);
				break;
			case R.id.bt_me:
				//TO SELF ACTIVITY
				ActivityUtils.ActivitySkip(this, SelfInfoActivity.class);
				break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		initList();
	}

	@Override
	protected void onPause() {
		super.onPause();
		CursorUtils.closeExistsCursor(cursor);
		mainTableUtils.closeDataBase();
	}

	protected void onStop() {
		super.onStop();
		CursorUtils.closeExistsCursor(cursor);
		mainTableUtils.closeDataBase();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CursorUtils.closeExistsCursor(cursor);
		mainTableUtils.closeDataBase();
	}
}
