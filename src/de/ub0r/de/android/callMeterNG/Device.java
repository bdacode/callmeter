/*
 * Copyright (C) 2010 Cyril Jaquier, Felix Bechstein
 * 
 * This file is part of NetCounter.
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; If not, see <http://www.gnu.org/licenses/>.
 */

package de.ub0r.de.android.callMeterNG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.os.Build;
import android.util.Log;

/**
 * Representation of a device.
 */
public abstract class Device {
	/** Tag for output. */
	private static final String TAG = "CallMeter.device";

	/** Single instance. */
	private static Device instance = null;
	/** Device's interfaces. */
	private String[] mInterfaces = null;

	/**
	 * @return single instance
	 */
	public static synchronized Device getDevice() {
		if (instance == null) {
			Log.i(TAG, "Device: " + Build.DEVICE);
			// All the devices we know about.
			Device[] allDevices = { new DefaultDevice(), new GenericDevice(),
					new SamsungI7500Device(), new PulseDevice(),
					new DroidDevice() };
			// Iterates over all the devices and try to found the corresponding
			// one.
			for (Device device : allDevices) {
				if (Arrays.asList(device.getNames()).contains(Build.DEVICE)) {
					instance = device;
					break;
				}
			}
			// Nothing found? Use the default device.
			if (instance == null) {
				instance = allDevices[0];
			}
		}
		Log.d(TAG, instance.getClass().getName());
		return instance;
	}

	/**
	 * @return device's names
	 */
	public abstract String[] getNames();

	/**
	 * @return device's device file: cell
	 */
	public abstract String getCell();

	/**
	 * @return device's device file: wifi
	 */
	public abstract String getWiFi();

	/**
	 * @return device's device file: bluetooth
	 */
	public abstract String getBluetooth();

	/**
	 * @return device's interfaces
	 */
	public final synchronized String[] getInterfaces() {
		if (this.mInterfaces == null) {
			List<String> tmp = new ArrayList<String>();
			if (this.getCell() != null) {
				tmp.add(this.getCell());
			}
			if (this.getWiFi() != null) {
				tmp.add(this.getWiFi());
			}
			if (this.getBluetooth() != null) {
				tmp.add(this.getBluetooth());
			}
			this.mInterfaces = tmp.toArray(new String[tmp.size()]);
		}
		return this.mInterfaces;
	}
}

/**
 * Generic device implementation corresponding to the emulator.
 */
class GenericDevice extends Device {
	@Override
	public String[] getNames() {
		return new String[] { "generic" };
	}

	@Override
	public String getBluetooth() {
		return null;
	}

	@Override
	public String getCell() {
		return this.getWiFi(); // for debugging purpose
	}

	@Override
	public String getWiFi() {
		return "eth0";
	}
}

/**
 * Default device implementation corresponding to the HTC Dream and HTC Magic.
 */
class DefaultDevice extends Device {
	@Override
	public String[] getNames() {
		// TODO Get the device name of the HTC Magic.
		return new String[] { "dream" };
	}

	@Override
	public String getBluetooth() {
		return "bnep0";
	}

	@Override
	public String getCell() {
		return "rmnet0";
	}

	@Override
	public String getWiFi() {
		return "tiwlan0";
	}
}

/**
 * Device implementation for the Samsung I7500. Also works with the I5700
 * (Spica).
 */
class SamsungI7500Device extends Device {
	@Override
	public String[] getNames() {
		return new String[] { "GT-I7500", "spica" };
	}

	@Override
	public String getBluetooth() {
		return "bnep0";
	}

	@Override
	public String getCell() {
		return "pdp0";
	}

	@Override
	public String getWiFi() {
		return "eth0";
	}
}

/**
 * Device implementation for the T-Mobile Pulse (Huawei U8220). Also works for
 * the Google Nexus One.
 */
class PulseDevice extends Device {
	@Override
	public String[] getNames() {
		return new String[] { "U8220", "passion" };
	}

	@Override
	public String getBluetooth() {
		return "bnep0";
	}

	@Override
	public String getCell() {
		return "rmnet0";
	}

	@Override
	public String getWiFi() {
		return "eth0";
	}
}

/**
 * Device implementation for the Motorola Droid.
 */
class DroidDevice extends Device {
	@Override
	public String[] getNames() {
		return new String[] { "sholes" };
	}

	@Override
	public String getBluetooth() {
		return "bnep0";
	}

	@Override
	public String getCell() {
		return "ppp0";
	}

	@Override
	public String getWiFi() {
		return "tiwlan0";
	}
}

/**
 * Device implementation for the LG Eve Android GW620R.
 */
class EveDevice extends Device {
	@Override
	public String[] getNames() {
		return new String[] { "EVE" };
	}

	@Override
	public String getBluetooth() {
		return "bnep0";
	}

	@Override
	public String getCell() {
		return "rmnet0";
	}

	@Override
	public String getWiFi() {
		return "wlan0";
	}
}
