package com.easy.example.activity.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.easy.activity.EasyActivity;
import com.easy.example.R;
import com.easy.example.activity.BaseActivity;
import com.easy.fragment.EasyFragment;
import com.easy.view.EasyFragmentPager;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_fragment_pager)
public class FragmentPagerActivity extends BaseActivity {

	@ViewInject(R.id.fp)
	private EasyFragmentPager fp;
	@ViewInject(R.id.rg)
	private RadioGroup rg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle b = null;
		b = new Bundle();
		b.putInt("index", 1);
		fp.addPage(Fragment.instantiate(this, SimpleFragment.class.getName(), b));
		b = new Bundle();
		b.putInt("index", 2);
		fp.addPage(Fragment.instantiate(this, SimpleFragment.class.getName(), b));
		b = new Bundle();
		b.putInt("index", 3);
		fp.addPage(Fragment.instantiate(this, SimpleFragment.class.getName(), b));
		b = new Bundle();
		b.putInt("index", 4);
		fp.addPage(Fragment.instantiate(this, SimpleFragment.class.getName(), b));
		fp.setRadioGroup(rg);
	}

	public static class SimpleFragment extends EasyFragment {
		@Override
		public View onCreateViewForReuse(LayoutInflater inflater, ViewGroup container, Bundle
				savedInstanceState) {
			TextView tv = new TextView(getActivity());
			tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT));
			tv.setGravity(Gravity.CENTER);
			tv.setTextSize(getActivity().getResources().getDimension(R.dimen.w15));
			tv.setText("Fragment" + getArguments().getInt("index", 0));
			return tv;
		}
	}
}
