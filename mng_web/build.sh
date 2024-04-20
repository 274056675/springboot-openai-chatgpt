#!/bin/bash 

echo "打包文件"
yarn build
echo "传输文件"
scp -r ./dist/** pig@139.224.200.249:/data/avue/sword
echo "部署成功"