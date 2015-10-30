/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package net.doo.maps;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;

import net.doo.maps.baidu.R;

import java.util.Set;

import static java.util.Collections.unmodifiableSet;
import static java.util.EnumSet.of;


/**
 * @see MapPresenter
 */
public class MapView extends MapContainerView {

	private BaiduMap anyMap;

	public MapView(Context context) {
		this(context, null);
	}

	public MapView(Context context, AttributeSet attrs) {
		super(context, attrs);

		initView(context, attrs);
	}

	public static Set<AnyMap.Capability> getCapabilities() {
		return unmodifiableSet(of(AnyMap.Capability.MAP_TYPES, AnyMap.Capability.TRAFFIC_LAYER));
	}

	private void initView(Context context, AttributeSet attrs) {
		com.baidu.mapapi.map.MapView mapView = new com.baidu.mapapi.map.MapView(context, attrs);
		addView(mapView);

		mapView.showZoomControls(false);
		anyMap = new BaiduMap(mapView);

		setClipToPadding(false);

		applyAttributes(context, attrs);
	}

	private void applyAttributes(Context context, AttributeSet attrs) {
		if (attrs == null) {
			return;
		}

		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MapView);
		try {
			boolean liteMode = typedArray.getBoolean(R.styleable.MapView_anyMapLiteMode, false);

			anyMap.getUiSettings().setAllGesturesEnabled(!liteMode);
		} finally {
			typedArray.recycle();
		}
	}

	@Override
	public AnyMap getMap() {
		return anyMap;
	}

	@Override
	public void getMapAsync(final OnMapReadyCallback callback) {
		post(new Runnable() {
			@Override
			public void run() {
				callback.onMapReady(anyMap);
			}
		});
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Do nothing
	}

	@Override
	public void onResume() {
		anyMap.getNativeMapView().onResume();
	}

	@Override
	public void onPause() {
		anyMap.getNativeMapView().onPause();
	}

	@Override
	public void onDestroy() {
		anyMap.getNativeMapView().onDestroy();
	}

	@Override
	public void onLowMemory() {
		// Do nothing
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Do nothing
	}
}
