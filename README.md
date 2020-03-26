# m3u8gen  
m3u8 playlist generator based on mohamnag/nginx-file-browser - allow play music on (android) VLC  

## Install

1. Install docker, docker-compose (get new versions)   

2. Download docker-compose.yml  

3. Start (in directory with docker-compose.yml)  
WEB_PORT=7001 PLAYLIST_PORT=7002 MUSIC_PATH="/my/path/to/music" HOST_NAME="<you_host_name>" docker-compose -p m3u8gen up -d  
Stop  
WEB_PORT=7001 PLAYLIST_PORT=7002 MUSIC_PATH="/my/path/to/music" HOST_NAME="<you_host_name>" docker-compose -p m3u8gen down   


## Howto use
Open in (android) VLC or other players that supports M3U8 
http://<you_host_name>:7002/playlist   
