package cy.test.rotate3d;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class RotateActivity extends Activity implements OnTouchListener  {
	private ViewGroup layoutmain;
	private ViewGroup layoutnext;
	private ViewGroup layoutlast;
	
	private Rotate3D rotate3d;
	private Rotate3D rotate3d2;
	private Rotate3D rotate3d3;
	private int mCenterX ;		
	private int mCenterY ;		
	private float degree = (float) 0.0;
	private int currentTab = 0;
	private float perDegree;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMain();
        DisplayMetrics dm = new DisplayMetrics();
		dm = getResources().getDisplayMetrics();
		mCenterX = dm.widthPixels / 2;
		mCenterY = dm.heightPixels / 2;
		perDegree = (float) (90.0 / dm.widthPixels);
	}
	private void initMain(){
        setContentView(R.layout.main);
        layoutnext = (ViewGroup) findViewById(R.id.layout_next);
        layoutnext.setOnTouchListener(this);
        
        layoutlast = (ViewGroup) findViewById(R.id.layout_last);
        layoutlast.setOnTouchListener(this);

		layoutmain = (LinearLayout)findViewById(R.id.layout_main);
		layoutmain.setOnTouchListener(this);
		
	}
	
	


	private int mLastMotionX;
	public boolean onTouch(View arg0, MotionEvent event) {
		int x = (int) event.getX();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mLastMotionX = x;
			break;
		case MotionEvent.ACTION_MOVE:
			int dx = x - mLastMotionX;
			if(dx != 0){
				doRotate(dx);
				if(degree > 90){			
					degree = 0;		
					break;
				}
			}
			mLastMotionX = x;
			break;
		case MotionEvent.ACTION_UP:
			endRotate();	
			break;
		}
		return true;
	}
	private void endRotate() {
		initMain();
		if(degree > 45){
			currentTab =(currentTab - 1)%3;
			if(currentTab < 0){
				currentTab = 2;
			}
		}else if(degree < -45){
			currentTab = (currentTab + 1)%3;
		}
		System.out.println(">>>>>>>>degree:"+degree +" currentTab:" + currentTab);
		if(currentTab == 0){
			layoutmain.setVisibility(View.VISIBLE);
			layoutnext.setVisibility(View.GONE);
			layoutlast.setVisibility(View.GONE);
		}else if(currentTab == 1){
			layoutmain.setVisibility(View.GONE);
			layoutnext.setVisibility(View.VISIBLE);
			layoutlast.setVisibility(View.GONE);
		}else if(currentTab == 2){
			layoutmain.setVisibility(View.GONE);
			layoutnext.setVisibility(View.GONE);
			layoutlast.setVisibility(View.VISIBLE);
		}
		
		degree = 0;
	}
	private void doRotate(int dx) {
		float xd = degree;
		layoutnext.setVisibility(View.VISIBLE);
		layoutmain.setVisibility(View.VISIBLE);
		layoutlast.setVisibility(View.VISIBLE);
		
		degree += perDegree*dx;
		rotate3d = new Rotate3D(xd , degree , 0, mCenterX, mCenterY);
		rotate3d2 = new Rotate3D( 90 + xd,  90+degree,0, mCenterX, mCenterY);
		rotate3d3 = new Rotate3D(-90+xd, -90+degree,0, mCenterX, mCenterY);	
		if(currentTab == 0){
			layoutmain.startAnimation(rotate3d);
			layoutnext.startAnimation(rotate3d2);
			layoutlast.startAnimation(rotate3d3);
		}else if(currentTab == 1){
			layoutmain.startAnimation(rotate3d3);
			layoutnext.startAnimation(rotate3d);
			layoutlast.startAnimation(rotate3d2);
		}else if(currentTab == 2){
			layoutmain.startAnimation(rotate3d2);
			layoutnext.startAnimation(rotate3d3);
			layoutlast.startAnimation(rotate3d);
		}
		rotate3d.setFillAfter(true);
		rotate3d2.setFillAfter(true);
		rotate3d3.setFillAfter(true);
	}
}
