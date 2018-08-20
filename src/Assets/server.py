import socket

import io

import picamera
import picamera.array
import time

from skimage.color import rgb2gray
from skimage import data, color
from skimage.transform import rescale, resize, downscale_local_mean

host = "192.168.0.104"
port = 5001

capture_width = 256
capture_height = 192

grayscape=True

class StringBuilder(object):
  def __init__(self):
    self._stringio = io.StringIO()
  def __str__(self):
    return self._stringio.getvalue()
  def append(self, *objects, sep=' ', end=''):
    print(*objects, sep=sep, end=end, file=self._stringio)

server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
server.bind((host,port))

print ("Start server " + str(host) + ":" + str(port)+"\n")

server.listen(1)
conn, addr = server.accept()
print ("Connection from: " + str(addr))

with picamera.PiCamera() as camera:
    camera.resolution = (416, 304)
    camera.framerate = 1
    
    #stream = io.BytesIO()
    #for foo in camera.capture_continuous(stream, format='jpeg', resize=None):
    with picamera.array.PiRGBArray(camera) as stream:
        for c in range(1,3):
            print ("Capturing "+str(c)+" ...")
            camera.capture(stream, format='bgr')
            print ("Preparing...")
            image = stream.array
            #print (image)
            if grayscape:
              image = rgb2gray(image)

            image = resize(image
                , (capture_width, capture_height)
                , anti_aliasing=True
                , mode = 'reflect'
            )
            
            #print (image)
            data = image.tostring()

            sb = StringBuilder()
            sb.append('capture:'+str(capture_width)+'x'+str(capture_height)+',buff=')

            cksum=0
            first=True
            for i in range(0, capture_width):
              for j in range(0, capture_height):
                if grayscape:
                  val = int((255/100.0)*(image[i][j]*100))
                  cksum += val
                  if first==False:
                    sb.append(',')
                  first=False
                  sb.append(str(val))
                else:
                  for k in range(0, 3):
                    val = int((255/100.0)*(image[i][j][k]*100))
                    cksum += val
                    if first==False:
                      sb.append(',')
                    first=False
                    sb.append(str(val))
                  
            sb.append(';checksum=')
            sb.append(cksum%126)
            sb.append("\n")
            
            data=str(sb)
            print ("Sending...")
            conn.send(data.encode())
                #gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
                #cv2.imshow('frame', gray)
                #if cv2.waitKey(1) & 0xFF == ord('q'):
                #    break
                # reset the stream before the next capture
            stream.seek(0)
            stream.truncate()
    
    #data = conn.recv(1024).decode()
    #if not data:
    #    break
    #print ("from connected  user: " + str(data))
    
    #data = str(data).upper()
    
    
conn.close()

server.shutdown(socket.SHUT_RDWR)
server.close()
print("closed")
