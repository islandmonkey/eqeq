/*
The MIT License (MIT)

Copyright (c) 2013 Aidan Fell

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.islandmonkey.eqeq;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

/**
 * Helper class for showing and cancelling EQEq notifications.
 * <p>
 * This class makes heavy use of the {@link NotificationCompat.Builder} helper
 * class to create notifications in a backward-compatible way.
 */
public class EQEQIsRunning {
	/**
	 * The unique identifier for this type of notification.
	 */
	private static final String NOTIFICATION_TAG = "EQEQIsRunning";

	/**
	 * Shows the notification, or updates a previously shown notification of
	 * this type, with the given parameters.
	 * @see #cancel(Context)
	 */
	public static void notify(final Context context, final int number) {
		final Resources res = context.getResources();

		// This image is used as the notification's large icon (thumbnail).
		final Bitmap picture = BitmapFactory.decodeResource(res,
				R.drawable.eqeqicon);

		final String title = res.getString(
				R.string.eqeq_is_running_notification_title);
		final String text = res.getString(
				R.string.eqeq_is_running_notification_text);

		final NotificationCompat.Builder builder = new NotificationCompat.Builder(
				context)

				// Set appropriate defaults for the notification light, sound,
				// and vibration.
				.setDefaults(Notification.PRIORITY_LOW)

				// Set required fields, including the small icon, the
				// notification title, and text.
				.setSmallIcon(R.drawable.eqeqicon)
				.setContentTitle(title).setContentText(text)

				// All fields below this line are optional.

				// Use a default priority (recognised on devices running Android
				// 4.1 or later)
				// Provide a large icon, shown with the notification in the
				// notification drawer on devices running Android 3.0 or later.
				.setLargeIcon(picture)

				// Set the pending intent to be initiated when the user touches
				// the notification.
				.setContentIntent(PendingIntent.getActivity(context, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT))
				
				// This is an ongoing notification
				.setOngoing(true)

				// Automatically dismiss the notification when it is touched.
				.setAutoCancel(false);
		
				

		notify(context, builder.build());
	}

	@TargetApi(Build.VERSION_CODES.ECLAIR)
	private static void notify(final Context context,
			final Notification notification) {
		final NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
			nm.notify(NOTIFICATION_TAG, 0, notification);
		} else {
			nm.notify(NOTIFICATION_TAG.hashCode(), notification);
		}
	}

	/**
	 * Cancels any notifications of this type previously shown using
	 * {@link #notify(Context, String, int)}.
	 */
	@TargetApi(Build.VERSION_CODES.ECLAIR)
	public static void cancel(final Context context) {
		final NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
			nm.cancel(NOTIFICATION_TAG, 0);
		} else {
			nm.cancel(NOTIFICATION_TAG.hashCode());
		}
	}
}