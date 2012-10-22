#! /usr/bin/python

# panorama simulation (street view)

import cv
import cv2
import math
import numpy as np


# ????
def remapping_pixel(x, stitch_pic, width):
	return showWidth_src/2 - 4*showWidth/stitch_pic/math.pi * np.arctan(stitch_pic*math.pi*(showWidth/2 - x)/4/showWidth)

def update_map(width, height, stitch_pic, src_img):
	# map_x, map_y: 
	# stitch_pic: int
	xx, yy = np.meshgrid(np.arange(width, dtype=np.float32), np.arange(height, dtype=np.float32))
	#map_x = remapping_pixel(xx, stitch_pic, width)	# not used
	map_x = xx
	map_y = yy
	#map_x = cv.fromarray(map_x); map_y = cv.fromarray(map_y)
	dst_img = cv2.remap(np.asarray(cv.GetMat(src_img)), map_x, map_y, cv2.INTER_CUBIC)
	return dst_img
	
def set_ROI_and_Map(title, src_img,dst_img, key, imgWidth, imgHeight, showWidth, showHeight, src_x = 0, src_y = 0):
	
	step_horiz = int(float(imgWidth)/48)
	step_verti = int(float(imgHeight)/8)
	if(str(key) == '65361'):		# left
		src_x -= step_horiz
		updated = True
	elif(str(key) == '65363'):		# right
		src_x += step_horiz
		updated = True
	elif(str(key) == '65362'):		# up
		src_y -= step_verti
		updated = True
	elif(str(key) == '65364'):		# down
		src_y += step_verti
		updated = True
	elif(key == 'ShowImage'):		# default: center
		src_x = int((float(imgWidth) - showWidth)/2); src_y = int((float(imgHeight) - showHeight)/2)	# center showing
		updated = True
	else:	# other illegal inputs or null
		updated = False
	# source ROI
	if not updated:
		return dst_img, src_x, src_y
	src_x = max(0, min(src_x, imgWidth - showWidth))
	src_y = max(0, min(src_y, imgHeight - showHeight))	# roi never out of the window
	rect_src = (src_x, src_y, showWidth, showHeight)	# rectangle area of source image
	cv.SetImageROI(src_img, rect_src)
	sub_img = cv.CreateImage( (showWidth, showHeight), src_img.depth, src_img.channels );
	cv.Copy(src_img,sub_img);
	# remapping
	angle = float(imgWidth/showWidth)
	dst_img = update_map(showWidth, showHeight, stitch_pic, sub_img)
	# show image
	cv2.imshow(title, dst_img)
	cv.ResetImageROI(src_img)
	return dst_img, src_x, src_y

def myShowImageKey(title, src_img, stitch_pic = 8, winWidth = 1400, winHeight = 700):
	# title: string
	# src_img: IplImage
	# stitch_pic, winWidth, winHeight: int
	imgWidth = src_img.width; imgHeight = src_img.height
	barWidth = 20		# bar width
	scale_w = float(imgWidth) / float(winWidth)	# scaled width
	scale_h = float(imgHeight) / float(winHeight)	# scaled height

	if(scale_w<=1): 
		winWidth = imgWidth
	if(scale_h<=1) :
		winHeight = imgHeigh

	showWidth = winWidth; showHeight = winHeight 	# rect_dst size
	src_x = 0; src_y = 0							# rect_src position initial

	# size of source > size of window
	if (scale_w>1.0 or scale_h>1.0):
		dst_img = cv.CreateImage((winWidth, winHeight), src_img.depth, src_img.channels)
		cv.Zero(dst_img)					
		showWidth = min(showWidth, imgWidth); showHeight = min(showHeight, imgHeight)
		# window ROI
		rect_dst = (0, 0, showWidth, showHeight)	# window of view
		cv.SetImageROI(dst_img, rect_dst)
		key = 'ShowImage'
		dst_img, src_x, src_y = set_ROI_and_Map(title, src_img, dst_img, key, imgWidth, imgHeight, showWidth, showHeight, src_x, src_y)
		while str(key) != '27':
			key = cv.WaitKey()
			dst_img, src_x, src_y = set_ROI_and_Map(title, src_img, dst_img, key, imgWidth, imgHeight, showWidth, showHeight, src_x, src_y)
			
	# size of source < size of window
	else:
		while 1:
			cv.ShowImage(title, src_img);
			key = cv.WaitKey()
			if(str(key) == '27'):
				break

filename = 'left_1.jpg'
width = 1000; height = 800; stitch_pic = 6

cv.NamedWindow("Image Scroll Bar", 1)

image = cv.LoadImage( filename, cv.CV_LOAD_IMAGE_COLOR )
if( not image ):
	print(stderr, "Can not load %s and/or %s/n", filename)
	exit()

myShowImageKey("Image Scroll Bar", image, stitch_pic, width, height)

cv.DestroyWindow("Image Scroll Bar")
exit()
