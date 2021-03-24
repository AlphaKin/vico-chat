cd /Users/jinziyu/CodeSave/vico/vico-chat/vico-server/vico-im-server-10002/src/main/java
protoc --proto_path=./org/vico/im/proto --java_out=. ./org/vico/im/proto/ProtoMessage.proto
protoc --js_out=import_style=commonjs,binary:. ./org/vico/im/proto/ProtoMessage.proto
sudo mv org/vico/im/proto/ProtoMessage_pb.js ~/CodeSave/vico/vico-chat/client/vico-desktop/src/renderer/assets/js/ImSupport/src/proto