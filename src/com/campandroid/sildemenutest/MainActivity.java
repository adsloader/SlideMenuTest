package com.campandroid.sildemenutest;

import java.io.IOException;
import java.util.Locale;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			return PlaceholderFragment.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 5;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		
		ImageView imgShare;
		ImageView imgBanner;
		
		int nSelection;
		
		// 원본 URL의 정보를 배열로 가지고 있다.
		String URL_LIST[] = {
				"https://mirror.enha.kr/wiki/%EA%B3%A0%EC%96%91%EC%9D%B4", 
				"https://mirror.enha.kr/wiki/%EA%B3%A0%EC%96%91%EC%9D%B4", 
				"https://mirror.enha.kr/wiki/%EC%B1%85",
				"https://mirror.enha.kr/wiki/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C",
				"https://mirror.enha.kr/wiki/%EA%B3%BC%EC%9D%BC",
				"http://blog.naver.com/adsloader"
				};

		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			
			// 나를 생성할 때, 넘겨진 값 중에 ARG_SECTION_NUMBER을 가져온다. 
			Bundle args = getArguments();
			nSelection = args.getInt(ARG_SECTION_NUMBER);
			
			TextView txtTitle = (TextView)rootView.findViewById(R.id.section_label);
			txtTitle.setText(" " + nSelection + " page");
			TextView txtContent = (TextView)rootView.findViewById(R.id.txtContent);
			
			
			// 페이지에 맞는 문자열을 가져온다.
			String sContent = getPageText(getActivity().getApplicationContext(), nSelection);
			
			txtContent.setText(sContent);
			
			imgShare = (ImageView)rootView.findViewById(R.id.imgShare);
			imgShare.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String sMessage = ">" + nSelection + "의 원본 주소입니다."; 
					Context ctx = getActivity().getApplicationContext();
					Toast.makeText(ctx, sMessage, Toast.LENGTH_LONG).show();
					
					// 선택된 페이지의 URL로 이동한다.
					BrowsedUrl(URL_LIST[nSelection]);
					
				}
			});
			
			imgBanner = (ImageView)rootView.findViewById(R.id.imgBanner);
			
			// 2페이지이면 그림없음
			if(nSelection == 5){ 
				imgBanner.setVisibility(View.GONE);
			} else {
				int [] RES_ID = 
					{R.drawable.cat,   R.drawable.cat,   R.drawable.books, R.drawable.androboy, 
					 R.drawable.fruit, R.drawable.books};
				imgBanner.setImageResource(RES_ID[nSelection]);
			}
			
			return rootView;
		}
		
		// URL로 이동한다.
		void BrowsedUrl(String sUrl){
			Intent i = new Intent(Intent.ACTION_VIEW); 
			Uri u = Uri.parse(sUrl);
			i.setData(u);

			startActivity(i);
		}
		
		
		// 페이지에 맞는 문자열을 가져온다.
		String getPageText(Context ctx, int nIndex){
			String [] PAGES = {"p1.txt", "p1.txt", "p2.txt", "p3.txt", "p4.txt", "p5.txt"};
			String sContent = "";
			try {
				sContent = PageReader.readPage(getActivity().getApplicationContext(), PAGES[nIndex]);
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return sContent;
		}
	}
	
	

}
