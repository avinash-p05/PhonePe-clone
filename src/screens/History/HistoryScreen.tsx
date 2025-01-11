import React from 'react';
import { View, Text, StyleSheet, ScrollView, TouchableOpacity, Image } from 'react-native';
import { Feather } from '@expo/vector-icons';
import styles from "./History.styles";
import colors from "../../styles/colors";
import Header from "../../components/Header";

// Types for transaction data
interface Transaction {
    id: string;
    type: 'payment' | 'received' | 'recharge' | 'bill';
    name: string;
    date: string;
    amount: number;
    status: 'success' | 'pending' | 'failed';
    category?: string;
    upiId?: string;
}

interface HistorySectionProps {
    onTransactionPress?: (transaction: Transaction) => void;
    onFilterPress?: () => void;
}

const TransactionItem: React.FC<{
    transaction: Transaction;
    onPress: (transaction: Transaction) => void;
}> = ({ transaction, onPress }) => {
    const getIconName = (type: Transaction['type']): keyof typeof Feather.glyphMap => {
        switch (type) {
            case 'payment':
                return 'arrow-up-right';
            case 'received':
                return 'arrow-down-left';
            case 'recharge':
                return 'smartphone';
            case 'bill':
                return 'file-text';
            default:
                return 'arrow-up-right';
        }
    };

    const getStatusColor = (status: Transaction['status']) => {
        switch (status) {
            case 'success':
                return colors.success;
            case 'pending':
                return '#FFA000';
            case 'failed':
                return colors.error;
            default:
                return colors.success;
        }
    };

    return (
        <TouchableOpacity
            style={styles.transactionItem}
            onPress={() => onPress(transaction)}
        >
            <View style={styles.iconContainer}>
                <Feather
                    name={getIconName(transaction.type)}
                    size={24}
                    color="#5C338E"
                />
            </View>

            <View style={styles.transactionDetails}>
                <View style={styles.transactionMain}>
                    <Text style={styles.transactionName}>{transaction.name}</Text>
                    <Text
                        style={[
                            styles.transactionAmount,
                            { color: transaction.type === 'received' ? colors.success : colors.gray }
                        ]}
                    >
                        {transaction.type === 'received' ? '+' : '-'} ₹{transaction.amount}
                    </Text>
                </View>

                <View style={styles.transactionSubDetails}>
                    <Text style={styles.transactionDate}>{transaction.date}</Text>
                    <View style={[styles.statusBadge, { backgroundColor: getStatusColor(transaction.status) }]}>
                        <Text style={styles.statusText}>{transaction.status}</Text>
                    </View>
                    {transaction.upiId && (
                        <Text style={styles.upiId}>{transaction.upiId}</Text>
                    )}
                </View>
            </View>
        </TouchableOpacity>
    );
};

const HistorySection: React.FC<HistorySectionProps> = ({
                                                           onTransactionPress = () => {},
                                                           onFilterPress = () => {},
                                                       }) => {
    // Sample transaction data
    const transactions: Transaction[] = [
        {
            id: '1',
            type: 'payment',
            name: 'Swiggy Order',
            date: '10 Jan, 2:30 PM',
            amount: 245,
            status: 'success',
            upiId: 'swiggy@upi'
        },
        {
            id: '2',
            type: 'received',
            name: 'Ganesh Kugaji',
            date: '10 Jan, 1:15 PM',
            amount: 1000,
            status: 'success',
            upiId: 'johndoe@upi'
        },
        {
            id: '3',
            type: 'recharge',
            name: 'Mobile Recharge',
            date: '9 Jan, 11:20 AM',
            amount: 199,
            status: 'pending',
            category: 'Mobile'
        },
        {
            id: '4',
            type: 'bill',
            name: 'Electricity Bill',
            date: '8 Jan, 4:45 PM',
            amount: 1450,
            status: 'success',
            category: 'Utility'
        }
    ];

    return (
        <View style={styles.container}>
            <Header/>
            <View style={styles.header}>
                <Text style={styles.title}>Transaction History</Text>
                <TouchableOpacity
                    style={styles.filterButton}
                    onPress={onFilterPress}
                >
                    <Feather name="filter" size={20} color="#5C338E" />
                    <Text style={styles.filterText}>Filter</Text>
                </TouchableOpacity>
            </View>

            <ScrollView style={styles.transactionList}>
                {transactions.map(transaction => (
                    <TransactionItem
                        key={transaction.id}
                        transaction={transaction}
                        onPress={onTransactionPress}
                    />
                ))}
            </ScrollView>
        </View>
    );
};


export default HistorySection;