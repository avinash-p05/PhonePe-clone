import {Platform, StatusBar, StyleSheet} from "react-native";
import colors from "../../styles/colors";
const styles = StyleSheet.create({
    main: {
        flex: 1,
        backgroundColor: colors.white,
    },
    container: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        backgroundColor: colors.primary,
        padding: 12,
    },
    leftSection: {
        flexDirection: 'row',
        alignItems: 'center',
        gap: 8,
    },
    avatarContainer: {
        width: 32,
        height: 32,
        borderRadius: 16,
        backgroundColor: 'white',
        justifyContent: 'center',
        alignItems: 'center',
        position: 'relative',
    },
    avatar: {
        width: 24,
        height: 24,
        borderRadius: 12,
    },
    indianFlag: {
        width: 12,
        height: 12,
        borderRadius: 6,
        backgroundColor: '#FF9933', // Indian flag orange
        position: 'absolute',
        bottom: -2,
        right: -2,
        borderWidth: 2,
        borderColor: '#5C338E',
    },
    addressSection: {
        marginLeft: 8,
    },
    addAddressRow: {
        flexDirection: 'row',
        alignItems: 'center',
        gap: 4,
    },
    addAddressText: {
        color: 'white',
        fontSize: 18,
        fontWeight: '600',
    },
    locationText: {
        color: '#E0E0E0',
        fontSize: 14,
    },
    rightSection: {
        flexDirection: 'row',
        alignItems: 'center',
        gap: 16,
    },
    notificationContainer: {
        position: 'relative',
    },
    badge: {
        position: 'absolute',
        top: -4,
        right: -4,
        backgroundColor: colors.success,
        width: 16,
        height: 16,
        borderRadius: 8,
        justifyContent: 'center',
        alignItems: 'center',
    },
    badgeText: {
        color: 'white',
        fontSize: 10,
        fontWeight: 'bold',
    },

});
export default styles;