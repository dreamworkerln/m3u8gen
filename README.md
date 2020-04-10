# m3u8gen  
m3u8 playlist generator based on mohamnag/nginx-file-browser - allow play music on (android) VLC  

## Install

1. Install docker, docker-compose (get new versions)   

2. Download docker-compose.yml, config, start.sh, stop.sh  

3. Configure config file. (Don't forget set right permission to /MY/PATH/TO/MUSIC/ files and folders)  

4. Start (in directory with docker-compose.yml)  
```  
./start.sh
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Stop
```  
./stop.sh
```

## Howto use
Open in (android) VLC or other players that supports M3U8 
```
http://<you_host_name>:7002/playlist
```   
