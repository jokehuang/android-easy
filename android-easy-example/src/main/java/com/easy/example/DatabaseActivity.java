package com.easy.example;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.easy.activity.EasyActivity;
import com.easy.db.EasyDbHelper;
import com.easy.db.EasySql;
import com.easy.db.EasySql.Constraint;
import com.easy.db.EasySql.Field;
import com.easy.db.EasySql.Type;
import com.easy.util.EmptyUtil;
import com.easy.util.ToastUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * @description
 * @author Joke
 * @email 113979462@qq.com
 * @create 2015年6月8日
 * @version 1.0.0
 */

@ContentView(R.layout.activity_database)
public class DatabaseActivity extends EasyActivity {
	@ViewInject(R.id.tv_user)
	private TextView tv_user;
	@ViewInject(R.id.et_id)
	private EditText et_id;
	@ViewInject(R.id.et_name)
	private EditText et_name;
	@ViewInject(R.id.rg_gender)
	private RadioGroup rg_gender;

	private MyDbHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dbHelper = new MyDbHelper(this);
		ViewUtils.inject(this);
	}

	/**
	 * 读取表里所有数据
	 * 
	 * @param v
	 */
	@OnClick(R.id.btn_read_all)
	public void onClickReadAll(View v) {
		List<User> users = dbHelper.readAll();
		StringBuilder sb = new StringBuilder();
		for (User user : users) {
			sb.append(user.toString() + "\n");
		}
		tv_user.setText(sb.toString());
	}

	/**
	 * 清空表里面的数据
	 * 
	 * @param v
	 */
	@OnClick(R.id.btn_delete_all)
	public void onClickDeleteAll(View v) {
		dbHelper.deleteAll();
		tv_user.setText("");
	}

	/**
	 * 根据指定的id读取表中的一条数据
	 * 
	 * @param v
	 */
	@OnClick(R.id.btn_read)
	public void onClickRead(View v) {
		if (EmptyUtil.isEmpty(et_id.getText().toString())) {
			ToastUtil.show(self, "请输入id");
		} else {
			User user = dbHelper.read(Integer.parseInt(et_id.getText()
					.toString()));
			if (user == null) {
				ToastUtil.show(self, "找不到结果");
			} else {
				et_name.setText(user.name);
				rg_gender.check(user.gender == 0 ? R.id.rb_female
						: R.id.rb_male);
			}
		}
	}

	/**
	 * 根据指定id删除表中的一条数据
	 * 
	 * @param v
	 */
	@OnClick(R.id.btn_delete)
	public void onClickDelete(View v) {
		if (EmptyUtil.isEmpty(et_id.getText().toString())) {
			ToastUtil.show(self, "请输入id");
		} else {
			int id = Integer.parseInt(et_id.getText().toString());
			if (dbHelper.delete(id)) {
				ToastUtil.show(self, "删除成功");
				onClickReadAll(v);
			} else {
				ToastUtil.show(self, "删除失败");
			}
			if (id > 0) {
				et_id.setText(--id + "");
			}
		}
	}

	/**
	 * 插入一条数据
	 * 
	 * @param v
	 */
	@OnClick(R.id.btn_insert)
	public void onClickInsert(View v) {
		if (EmptyUtil.isEmpty(et_id.getText().toString())) {
			ToastUtil.show(self, "请输入id");
		} else {
			User user = new User();
			user.name = et_name.getText().toString();
			user.gender = rg_gender.getCheckedRadioButtonId() == R.id.rb_female ? 0
					: 1;
			if (dbHelper.insert(user)) {
				ToastUtil.show(self, "插入成功");
				et_id.setText(user.id + "");
				onClickReadAll(v);
			} else {
				ToastUtil.show(self, "插入失败");
			}
		}
	}

	/**
	 * 更新数据
	 * 
	 * @param v
	 */
	@OnClick(R.id.btn_update)
	public void onClickUpdate(View v) {
		if (EmptyUtil.isEmpty(et_id.getText().toString())) {
			ToastUtil.show(self, "请输入id");
		} else {
			User user = new User();
			user.id = Integer.parseInt(et_id.getText().toString());
			user.name = et_name.getText().toString();
			user.gender = rg_gender.getCheckedRadioButtonId() == R.id.rb_female ? 0
					: 1;
			if (dbHelper.update(user)) {
				ToastUtil.show(self, "更新成功");
				onClickReadAll(v);
			} else {
				ToastUtil.show(self, "更新失败");
			}
		}

	}

	/**
	 * 继承EasyDbHelper再配合EasySql就能节省一些数据库代码
	 * 
	 * @author Joke
	 *
	 */
	class MyDbHelper extends EasyDbHelper {

		public MyDbHelper(Context context) {
			super(context, "database", 2);
		}

		@Override
		public void onCreate(SQLiteDatabase sqlitedatabase) {
			createTable(sqlitedatabase);
		}

		@Override
		public void onUpgrade(SQLiteDatabase sqlitedatabase, int i, int j) {
			dropTable(sqlitedatabase);
			createTable(sqlitedatabase);
		}

		/**
		 * 使用EasySql的方法创建建表语句
		 * 
		 * @param db
		 */
		public void createTable(SQLiteDatabase db) {
			List<Field> fields = new ArrayList<Field>();
			fields.add(new Field("id", Type.INTEGER, Constraint.PRIMARY_KEY));
			fields.add(new Field("name", Type.TEXT));
			fields.add(new Field("gender", Type.INTEGER));
			fields.add(new Field("createTime", Type.INTEGER));

			db.execSQL(EasySql.buildCreateTableSql("user", fields));
		}

		/**
		 * 使用EasySql的方法创建删除表语句
		 * 
		 * @param db
		 */
		public void dropTable(SQLiteDatabase db) {
			db.execSQL(EasySql.buildDropTableSql("user"));
		}

		/**
		 * 删除所有数据
		 */
		public void deleteAll() {
			agentWrite(new Writer() {
				@Override
				public boolean doWrite(SQLiteDatabase db) {
					db.delete("user", null, null);
					return true;
				}
			});
		}

		/**
		 * 在Cursor中读取中当前游标指定的一条数据
		 * 
		 * @param c
		 * @return
		 */
		private User read(Cursor c) {
			User user = new User();
			user.id = c.getLong(c.getColumnIndex("id"));
			user.name = c.getString(c.getColumnIndex("name"));
			user.gender = c.getInt(c.getColumnIndex("gender"));
			user.createTime = c.getLong(c.getColumnIndex("createTime"));
			return user;
		}

		/**
		 * 使用代理方式读取全部数据，不用手动关闭数据库
		 * 
		 * @return
		 */
		public List<User> readAll() {
			return agentRead(new Reader<List<User>>() {
				@Override
				public List<User> doRead(SQLiteDatabase db) {
					List<User> users = new ArrayList<User>();
					Cursor c = db.query("user", null, null, null, null, null,
							null);
					while (c.moveToNext()) {
						users.add(read(c));
					}
					return users;
				}
			});
		}

		/**
		 * 使用代理方式读取一条数据，不用手动关闭数据库
		 * 
		 * @return
		 */
		public User read(final int id) {
			return agentRead(new Reader<User>() {
				@Override
				public User doRead(SQLiteDatabase db) {
					Cursor c = db.query("user", null, "id = " + id, null, null,
							null, null);
					if (c.moveToNext()) {
						return read(c);
					} else {
						return null;
					}
				}
			});
		}

		/**
		 * 使用代理方式删除一条数据，不用手动关闭数据库
		 * 
		 * @return
		 */
		public boolean delete(final int id) {
			return agentWrite(new Writer() {
				@Override
				public boolean doWrite(SQLiteDatabase db) {
					return db.delete("user", "id = " + id, null) != 0;
				}
			});
		}

		/**
		 * 使用代理方式插入一条数据，不用手动关闭数据库
		 * 
		 * @return
		 */
		public boolean insert(final User user) {
			return agentWrite(new Writer() {
				@Override
				public boolean doWrite(SQLiteDatabase db) {
					user.createTime = System.currentTimeMillis();
					ContentValues values = new ContentValues();
					values.put("name", user.name);
					values.put("gender", user.gender);
					values.put("createTime", user.createTime);
					user.id = db.insert("user", null, values);
					return user.id != 0;
				}
			});
		}

		/**
		 * 使用代理方式更新一条数据，不用手动关闭数据库
		 * 
		 * @return
		 */
		public boolean update(final User user) {
			return agentWrite(new Writer() {
				@Override
				public boolean doWrite(SQLiteDatabase db) {
					ContentValues values = new ContentValues();
					values.put("name", user.name);
					values.put("gender", user.gender);
					return db.update("user", values, "id = " + user.id, null) != 0;
				}
			});
		}
	}

	class User {
		long id;
		String name;
		int gender;
		long createTime;

		@Override
		public String toString() {
			return "User [id=" + id + ", name=" + name + ", gender="
					+ (gender == 0 ? "female" : "male") + ", createTime="
					+ createTime + "]";
		}
	}
}
