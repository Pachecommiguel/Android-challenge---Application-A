# Android-challenge-Application-A

Application A is composed by two components: an InputFrame and a SenderConverter.
The InputFrame is a component that reads messages from an EditText. The
SenderConverter component converts each message to Hexadecimal and propagate it to
Application B.
The communication between the two components is based in a concurrent queue. After reading each message, the SenderConverter should
convert it into a Hexadecimal message and sent it to Application B. 
