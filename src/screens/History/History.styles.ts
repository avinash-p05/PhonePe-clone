import {Platform, StatusBar, StyleSheet} from "react-native";
import colors from "../../styles/colors";
const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: colors.background,
    },
    header: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        backgroundColor: colors.primary,
        paddingHorizontal: 16,
        paddingBottom: 16,
        paddingTop: Platform.OS === 'android' ? StatusBar.currentHeight : 0,
    },
    title: {
        fontSize: 18,
        fontWeight: '600',
        color: colors.white,
    },
    filterButton: {
        flexDirection: 'row',
        alignItems: 'center',
        gap: 4,
        padding: 8,
        borderRadius: 8,
        backgroundColor: '#F0E7F9',
    },
    filterText: {
        color: colors.primary,
        fontSize: 14,
        fontWeight: '500',
    },
    transactionList: {
        flex: 1,
    },
    transactionItem: {
        elevation:10,
        backgroundColor: colors.secondary,
        margin:8,
        flexDirection: 'row',
        padding: 16,
        borderRadius: 12,
        shadowColor: colors.secondary,
    },
    iconContainer: {
        width: 40,
        height: 40,
        borderRadius: 20,
        backgroundColor: '#F0E7F9',
        justifyContent: 'center',
        alignItems: 'center',
        marginRight: 12,
    },
    transactionDetails: {
        flex: 1,
    },
    transactionMain: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        marginBottom: 4,
    },
    transactionName: {
        fontSize: 16,
        fontWeight: '500',
        color: colors.white,
    },
    transactionAmount: {
        fontSize: 16,
        fontWeight: '600',
    },
    transactionSubDetails: {
        flexDirection: 'row',
        alignItems: 'center',
        gap: 8,
    },
    transactionDate: {
        fontSize: 12,
        color: colors.gray,
    },
    statusBadge: {
        paddingHorizontal: 6,
        paddingVertical: 2,
        borderRadius: 4,
    },
    statusText: {
        fontSize: 10,
        color: '#fff',
        textTransform: 'capitalize',
    },
    upiId: {
        fontSize: 12,
        color: colors.gray,
    },
});
export default styles;