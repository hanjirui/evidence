# evidence

## 环境
### Elasticsearch
* [Elasticsearch6.6.2](https://www.elastic.co/downloads/past-releases/elasticsearch-6-6-2)
  修改配置文件config/elasticsearch.yml，关闭cluster.name注释，并设置为 court-evidence
* [Elasticsearch ik分词插件6.6.2](https://github.com/medcl/elasticsearch-analysis-ik/releases/tag/v6.6.2),[安装参考](https://www.jianshu.com/p/8b0c055fd7be)
* ES根目录下执行启动命令 ./bin/elasticsearch -d
* 执行单元测试 EvidenceCreatIndexTest 创建相关索引

## 数据初始化及调试
* 调试数据使用测试用例 EvidenceLoaderTest，需要配置数据样本目录
* 初始化数据后，可运行service下单元测试，是否通过

## 启动服务
* 运行 EvidenceApplication