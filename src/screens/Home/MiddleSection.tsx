import React from 'react';
import { View, Text, ScrollView, TouchableOpacity } from 'react-native';
import { Feather } from '@expo/vector-icons';
import styles from "./MiddleSection.styles";
import colors from "../../styles/colors";

interface MenuItemProps {
    icon: keyof typeof Feather.glyphMap;
    title: string;
    badge?: string | number;
    onPress?: () => void;
}

interface SectionHeaderProps {
    title: string;
    showViewAll?: boolean;
    onViewAllPress?: () => void;
}

interface QuickActionProps {
    icon: keyof typeof Feather.glyphMap;
    title: string;
    badge?: string | number;
    onPress?: () => void;
}

const MenuItem: React.FC<MenuItemProps> = ({ icon, title, badge, onPress }) => (
    <TouchableOpacity style={styles.menuItem} onPress={onPress}>
        <View style={styles.iconContainer}>
            <Feather name={icon} size={24} color="white" />
        </View>
        <Text style={styles.menuItemText}>{title}</Text>
        {badge && (
            <View style={styles.badge}>
                <Text style={styles.badgeText}>{badge}</Text>
            </View>
        )}
    </TouchableOpacity>
);

const SectionHeader: React.FC<SectionHeaderProps> = ({
                                                         title,
                                                         showViewAll = true,
                                                         onViewAllPress
                                                     }) => (
    <View style={styles.sectionHeader}>
        <Text style={styles.sectionTitle}>{title}</Text>
        {showViewAll && (
            <TouchableOpacity onPress={onViewAllPress}>
                <Text style={styles.viewAllText}>View All →</Text>
            </TouchableOpacity>
        )}
    </View>
);

const QuickAction: React.FC<QuickActionProps> = ({
                                                     icon,
                                                     title,
                                                     badge,
                                                     onPress
                                                 }) => (
    <TouchableOpacity style={styles.quickActionItem} onPress={onPress}>
        <Feather name={icon} size={20} color= {colors.primary} />
        {badge && (
            <View style={styles.rewardBadge}>
                <Text style={styles.rewardBadgeText}>{badge}</Text>
            </View>
        )}
        <Text style={styles.quickActionText}>{title}</Text>
    </TouchableOpacity>
);

interface MiddleSectionProps {
    onTransferPress?: () => void;
    onRechargePress?: () => void;
    onLoanPress?: () => void;
    onInsurancePress?: () => void;
}

const MiddleSection: React.FC<MiddleSectionProps> = ({
                                                         onTransferPress,
                                                         onRechargePress,
                                                         onLoanPress,
                                                         onInsurancePress
                                                     }) => {
    return (
        <ScrollView style={styles.container} showsVerticalScrollIndicator={false}>
            {/* Transfer Money Section */}
            <View style={styles.section}>
                <Text style={styles.mainTitle}>Transfer Money</Text>
                <View style={styles.transferGrid}>
                    <MenuItem
                        icon="user"
                        title="To Mobile Number"
                        onPress={onTransferPress}
                    />
                    <MenuItem
                        icon="home"
                        title="To Bank/UPI ID"
                        onPress={onTransferPress}
                    />
                    <MenuItem
                        icon="refresh-cw"
                        title="To Self Account"
                        onPress={onTransferPress}
                    />
                    <MenuItem
                        icon="credit-card"
                        title="Check Bank Balance"
                        onPress={onTransferPress}
                    />
                </View>

                <View style={styles.upiSection}>
                    <TouchableOpacity style={styles.upiLiteButton}>
                        <Text style={styles.upiLiteText}>UPI Lite: Try Now</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.upiIdButton}>
                        <Text style={styles.upiIdText}>UPI ID: 900850058...</Text>
                        <Feather name="chevron-right" size={20} color="#666" />
                    </TouchableOpacity>
                </View>
            </View>

            <View style={styles.quickActions}>
                    <QuickAction
                        icon="credit-card"
                        title="PhonePe Wallet"
                    />
                    <QuickAction
                        icon="gift"
                        title="Explore Rewards"
                        badge="6"
                    />
                    <QuickAction
                        icon="share-2"
                        title="Refer & Get ₹200"
                    />

            </View>


            {/* Recharge & Pay Bills Section */}
            <View style={styles.section}>
                <SectionHeader
                    title="Recharge & Pay Bills"
                    onViewAllPress={onRechargePress}
                />
                <View style={styles.gridContainer}>
                    <MenuItem icon="zap" title="Mobile Recharge" />
                    <MenuItem icon="dollar-sign" title="Loan Repayment" />
                    <MenuItem icon="credit-card" title="Credit Card Payment" />
                    <MenuItem icon="home" title="Rent" />
                </View>
            </View>

            {/* Loan Section */}
            <View style={styles.section}>
                <SectionHeader
                    title="Loan"
                    onViewAllPress={onLoanPress}
                />
                <View style={styles.gridContainer}>
                    <MenuItem icon="user" title="Personal Loan" />
                    <MenuItem icon="activity" title="Credit Score" />
                    <MenuItem icon="database" title="Gold Loan" />
                    <MenuItem icon="trending-up" title="Mutual Fund Loan" />
                </View>
            </View>

            {/* Insurance Section */}
            <View style={styles.section}>
                <SectionHeader
                    title="Insurance"
                    onViewAllPress={onInsurancePress}
                />
            </View>
        </ScrollView>
    );
};



export default MiddleSection;