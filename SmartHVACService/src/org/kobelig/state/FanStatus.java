package org.kobelig.state;

import java.io.Serializable;

	/**
	 * @author  Hiroshi Igaki
	 */
	@SuppressWarnings("serial")
	public class FanStatus implements Serializable ,Status{

		private boolean power;
		private int air_volume;
		private boolean swing;
		private int timer;
		/**
		 * @return  timer
		 * @uml.property  name="timer"
		 */
		public int getTimer() {
			return timer;
		}
		/**
		 * @param timer  設定する timer
		 * @uml.property  name="timer"
		 */
		public void setTimer(int time) {
			this.timer = time;
		}
		/**
		 * @return  swing
		 * @uml.property  name="swing"
		 */
		public boolean getSwing() {
			return swing;
		}
		/**
		 * @param swing  設定する swing
		 * @uml.property  name="swing"
		 */
		public void setSwing(boolean sw) {
			this.swing = sw;
		}
		/**
		 * @return  power
		 * @uml.property  name="power"
		 */
		public boolean getPower() {
			return power;
		}
		/**
		 * @param power  設定する power
		 * @uml.property  name="power"
		 */
		public void setPower(boolean power) {
			this.power = power;
		}

		public int getAirVolume() {
			return air_volume;
		}

		public void setAirVolume(int volume) {
			this.air_volume = volume;
		}

		@Override
		public String toString() {
			return "FanStatus [power=" + power + ", air_volume=" + air_volume
					+ ", swing=" + swing + ", timer=" + timer + "]";
		}
		public void reset() {
			// TODO 自動生成されたメソッド・スタブ
			this.setPower(false);
			this.setSwing(false);
			this.setAirVolume(1);
			this.setTimer(0);
		}
		/**
		 * @return  FANStatus
		 * @uml.property  name="this"
		 */

	}