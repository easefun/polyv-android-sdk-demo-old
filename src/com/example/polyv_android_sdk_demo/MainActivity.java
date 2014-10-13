package com.example.polyv_android_sdk_demo;



import java.io.File;
import java.net.URL;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.easefun.polyvsdk.Downloader;
import com.easefun.polyvsdk.PolyvSDKClient;
import com.easefun.polyvsdk.Video;
import com.easefun.polyvsdk.VideoViewActivity;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
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
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		//===========AsyncTask class
		class LoadVideoTask extends AsyncTask<String, Void, String> {

		    @Override protected String doInBackground(String... params) {
		      // Do work
		    	Video video = PolyvSDKClient.getInstance().getVideo(params[0]);
				Log.d("w",video.getVid());
		      return video.getVid();
		    }

		    @Override protected void onPostExecute(String result) {
		      Log.d("LoadVideoTask", "Received result: " + result);
		    }
		 }
		class VideoUploadTask extends AsyncTask<String, Void, String> {

		    @Override protected String doInBackground(String... params) {
		        File path = new File(Environment.getExternalStorageDirectory(), "myRecording.mp4");
		        
		        try{
		        	Video video = PolyvSDKClient.getInstance().upload(path.toString(), "我的标题", "tag", "desc",0);
		        	return video.getVid();
		        }catch(Exception e){
		        	
		        }
		        return null;
				
		        
		    }

		    @Override protected void onPostExecute(String result) {
		      Log.d("VideoUploadTask", "video uploaded vid: " + result);
		    }
		 }
		
		class VideoDownloadTask extends AsyncTask<String, Void, String> {
			public void stopTask(){
				if(downloader!=null){
					downloader.stop();
				}
			}
			public Downloader downloader;
		    @Override protected String doInBackground(String... params) {
		        File path = new File(Environment.getExternalStorageDirectory(), "downloaded.mp4");
		       
		        try{
		        	downloader = new Downloader("sl8da4jjbxfa118270cb5c00d70802df_s",path);
		        	downloader.begin();
		        }catch(Exception e){
		        	e.printStackTrace();
		        }
		        return null;
				
		        
		    }

		    @Override protected void onPostExecute(String result) {
		      Log.d("downloadTask", "video downloaded: " + result);
		    }
		 }
		
		public PlaceholderFragment() {
		}
		ProgressDialog barProgressDialog;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			
			Button buttonRecord = (Button)rootView.findViewById(R.id.button_record);
			
			buttonRecord.setOnClickListener(buttonRecordOnClickListener); 
			
			Button button_upload = (Button)rootView.findViewById(R.id.button_upload);
			
			button_upload.setOnClickListener(button_upload_onClickListener); 
			
			PolyvSDKClient client = PolyvSDKClient.getInstance();
			client.setReadtoken("nsJ7ZgQMN0-QsVkscukWt-qLfodxoDFm");
			client.setWritetoken("Y07Q4yopIVXN83n-MPoIlirBKmrMPJu0");
			
			
			Button buttonTestDownload = (Button)rootView.findViewById(R.id.button_download);
			
			buttonTestDownload.setOnClickListener(downloadButtonOnClickListener); 
			
			
			
			Button buttonPlayLocal = (Button)rootView.findViewById(R.id.button_play_local);
			
			buttonPlayLocal.setOnClickListener(playLocalButtonOnClickListener);
			
			
			
			Button buttonPlayUrl = (Button)rootView.findViewById(R.id.button_play_url);
			
			buttonPlayUrl.setOnClickListener(playUrlButtonOnClickListener);
			
			
			return rootView;
			
			
		}
		
		private OnClickListener playLocalButtonOnClickListener = new OnClickListener(){
			@Override
			public void onClick(View v) {
				try{
		    		Intent videoPlaybackActivity = new Intent(getActivity(), VideoViewActivity.class);
		    		File path = new File(Environment.getExternalStorageDirectory(), "downloaded.mp4");
		    		videoPlaybackActivity.putExtra("path", path.toString());
		    		
		    		startActivityForResult(videoPlaybackActivity,1);
		    	}catch(Exception e){
		    		e.printStackTrace();
		    		//Log.d("v", e.getMessage());
		    		Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
		    		
		    	}
			}
		};
		private OnClickListener playUrlButtonOnClickListener = new OnClickListener(){
			@Override
			public void onClick(View v) {
				try{
		    		Intent videoPlaybackActivity = new Intent(getActivity(), VideoViewActivity.class);
		    		
		    		videoPlaybackActivity.putExtra("vid","sl8da4jjbx90c4e290eeb0deaedb3b11_s");
		    		
		    		startActivityForResult(videoPlaybackActivity,1);
		    	}catch(Exception e){
		    		e.printStackTrace();
		    		//Log.d("v", e.getMessage());
		    		Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
		    		
		    	}
			}
		};
		
		private OnClickListener downloadButtonOnClickListener = new OnClickListener(){
			@Override
			public void onClick(View v) {
				final VideoDownloadTask downloadTask = new VideoDownloadTask();
				
				
				barProgressDialog = new ProgressDialog(getActivity());
				barProgressDialog.setTitle("正在下载 ...");
				barProgressDialog.setProgressStyle(barProgressDialog.STYLE_HORIZONTAL);
				barProgressDialog.setProgress(0);
				barProgressDialog.setMax(100);
				
				barProgressDialog.setCancelable(true);
				barProgressDialog.setCanceledOnTouchOutside(false);
				
				barProgressDialog.setOnCancelListener(new OnCancelListener() {

			        public void onCancel(DialogInterface dialog) {
			        	if(downloadTask!=null){
			        	  downloadTask.stopTask();
			        	  Log.i("cancel","Cancel Called"); 
			        	}
			        
			        
			        }
			    });
				barProgressDialog.show();
				downloadTask.execute();
				
				
				new Thread(new Runnable() {
					@Override
					public void run() {
						while(true){
							if(downloadTask.downloader!=null){
								barProgressDialog.setProgress(downloadTask.downloader.getPercent());
							}
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (barProgressDialog.getProgress() == barProgressDialog.getMax()) {
								barProgressDialog.dismiss();
								break;
							}
							

						}
						 
						 
					}
				
						
				}).start();
			}
		};
		
		private OnClickListener buttonRecordOnClickListener = new OnClickListener(){
			@Override
			public void onClick(View v) {
				//Toast.makeText(getActivity(), "asdf", Toast.LENGTH_SHORT).show();
				Intent myIntent = new Intent(getActivity(), RecordActivity.class);
				getActivity().startActivity(myIntent);
			}
		};
		private OnClickListener button_upload_onClickListener = new OnClickListener(){
			@Override
			public void onClick(View v) {
				try{
					barProgressDialog = new ProgressDialog(getActivity());
					barProgressDialog.setTitle("正在上传 ...");
					barProgressDialog.setProgressStyle(barProgressDialog.STYLE_HORIZONTAL);
					barProgressDialog.setProgress(0);
					barProgressDialog.setMax(100);
					barProgressDialog.setCancelable(true);
					barProgressDialog.setCanceledOnTouchOutside(false);
					
					barProgressDialog.show();
					VideoUploadTask uploadTask = new VideoUploadTask();
					uploadTask.execute();
					
					new Thread(new Runnable() {
						@Override
						public void run() {
							while(true){
								barProgressDialog.setProgress(PolyvSDKClient.getInstance().getPercent());
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if (barProgressDialog.getProgress() == barProgressDialog.getMax()) {
									barProgressDialog.dismiss();
									break;
								}
								

							}
							 
							 
						}
					
							
					}).start();


					
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		};
		
	}

	
}



