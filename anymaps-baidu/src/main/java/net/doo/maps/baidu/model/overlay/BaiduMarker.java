/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package net.doo.maps.baidu.model.overlay;

import net.doo.maps.baidu.model.BaiduToModelConverter;
import net.doo.maps.baidu.model.ModelToBaiduConverter;
import net.doo.maps.model.BitmapDescriptor;
import net.doo.maps.model.LatLng;
import net.doo.maps.model.Marker;

public class BaiduMarker implements Marker {

	private final com.baidu.mapapi.map.Marker marker;
	private com.baidu.mapapi.model.LatLng tempPosition;


	public BaiduMarker(com.baidu.mapapi.map.Marker marker) {
		this.marker = marker;
		tempPosition = marker.getPosition();
		if (!marker.isVisible()) {
			hide();
		}

	}

	@Override
	public void setIcon(BitmapDescriptor icon) {
		marker.setIcon(ModelToBaiduConverter.convert(icon));
	}

	@Override
	public LatLng getPosition() {
		return BaiduToModelConverter.convert(marker.getPosition());
	}

	@Override
	public void showInfoWindow() {
		// Do nothing
	}

	private void hide() {
		marker.setVisible(false);
		marker.setPosition(new com.baidu.mapapi.model.LatLng(0, 0));
	}

	private void show() {
		marker.setPosition(tempPosition);
		marker.setVisible(true);
	}


	@Override
	public void setVisible(final boolean visible) {
		if (visible == marker.isVisible()) {
			return;
		}

		if (visible) {
			show();
		} else {
			hide();
		}

	}

	@Override
	public void remove() {
		marker.remove();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BaiduMarker)) return false;

		BaiduMarker that = (BaiduMarker) o;

		return marker.equals(that.marker);
	}

	@Override
	public int hashCode() {
		return marker.hashCode();
	}
}