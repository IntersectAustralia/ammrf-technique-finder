package org.ammrf.tf;

public enum MediaSection {
	LIST(160), OUTPUT(225), INSTRUMENT(225);

        private int max;

        private MediaSection(int max) {
		this.max = max;
        }

        public int width(int width, int height) {
		if (width > max) {
			height = (height * max) / width;
			width = max;
		}
		if (height > max) {
			width = (width * max) / height;
                }
		return width;
        }

        public int height(int width, int height) {
		if (width > max) {
			height = (height * max) / width;
			width = max;
		}
		if (height > max) {
			return max;
                }
		return height;
        }
}
