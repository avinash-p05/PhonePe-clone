import {Dimensions, StyleSheet} from "react-native";
import colors from "../../styles/colors";
const { width } = Dimensions.get('window');
const scannerWidth = width * 0.7;
const scannerHeight = scannerWidth;

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: 'black',
    },
    overlay: {
        flex: 1,
        backgroundColor: 'rgba(0,0,0,0.63)',
        alignItems: 'center',
        justifyContent: 'center',
    },
    scannerContainer: {
        backgroundColor: 'rgba(255,255,255,0.04)',
        width: scannerWidth,
        height: scannerHeight,
        position: 'relative',
    },
    corner: {
        position: 'absolute',
        width: 40,
        height: 40,
        borderRadius:8,
        borderColor: colors.primary,
        borderWidth: 8,
    },
    topLeft: {
        top: 0,
        left: 0,
        borderBottomWidth: 0,
        borderRightWidth: 0,
    },
    topRight: {
        top: 0,
        right: 0,
        borderBottomWidth: 0,
        borderLeftWidth: 0,
    },
    bottomLeft: {
        bottom: 0,
        left: 0,
        borderTopWidth: 0,
        borderRightWidth: 0,
    },
    bottomRight: {
        bottom: 0,
        right: 0,
        borderTopWidth: 0,
        borderLeftWidth: 0,
    },
    instructionText: {
        color: 'white',
        fontSize: 16,
        marginTop: 20,
        textAlign: 'center',
    },
    permissionText: {
        flex: 1,
        textAlign: 'center',
        textAlignVertical: 'center',
    }
});
export default styles;