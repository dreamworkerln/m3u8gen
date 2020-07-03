# m3u8gen  
m3u8 playlist generator based on mohamnag/nginx-file-browser - allow play music on (android) VLC  
Will rebuild playlist on changes in music directory (files uploaded/deleted)   

## Install

1. Install docker, docker-compose (get new versions)   

2. Download contents of docker-compose/ (config, docker-compose.yml, start.sh, stop.sh)  to some dir    

3. Configure config file. (Don't forget set right permission to /MY/PATH/TO/MUSIC/ files and folders)  

4. Start (in directory with docker-compose.yml)  
```  
./start.sh
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Stop
```  
./stop.sh
```
5. Optionally configure and install systemd module from systemd/ 


## Howto use
Open in (android) VLC or other players that supports M3U8 
```
http://<MY_HOST_NAME>:7002/playlist
```   
